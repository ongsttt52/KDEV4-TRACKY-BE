package kernel360.trackyweb.rent.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kernel360.trackycore.core.common.entity.RentEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RentRepositoryImpl implements RentRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<RentEntity> searchByFilter(String rentUuid, String rentStatus, LocalDateTime rentDate,
		Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// 데이터 조회 쿼리 생성
		CriteriaQuery<RentEntity> query = cb.createQuery(RentEntity.class);
		Root<RentEntity> rent = query.from(RentEntity.class);
		List<Predicate> predicates = setPredicates(rentUuid, rentStatus, rentDate, cb, rent);
		query.where(cb.and(predicates.toArray(new Predicate[0])));

		// Pageable에 정렬 조건이 없으면 기본 정렬 적용 (updatedAt이 존재하면 그 값, 아니면 createdAt)
		if (!pageable.getSort().isSorted()) {
			query.orderBy(cb.desc(
				cb.selectCase()
					.when(cb.isNotNull(rent.get("updatedAt")), rent.get("updatedAt"))
					.otherwise(rent.get("createdAt"))
			));
		}

		List<RentEntity> resultList = em.createQuery(query)
			.setFirstResult((int)pageable.getOffset())
			.setMaxResults(pageable.getPageSize())
			.getResultList();

		// 전체 건수를 위한 Count 쿼리 생성
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<RentEntity> countRoot = countQuery.from(RentEntity.class);
		List<Predicate> countPredicates = setPredicates(rentUuid, rentStatus, rentDate, cb, countRoot);
		countQuery.select(cb.count(countRoot))
			.where(cb.and(countPredicates.toArray(new Predicate[0])));
		Long total = em.createQuery(countQuery).getSingleResult();

		return new PageImpl<>(resultList, pageable, total);
	}

	private List<Predicate> setPredicates(String rentUuid, String rentStatus, LocalDateTime rentDate,
		CriteriaBuilder cb, Root<RentEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (rentUuid != null && !rentUuid.isBlank()) {
			predicates.add(cb.like(root.get("rentUuid"), "%" + rentUuid + "%"));
		}
		if (rentStatus != null && !rentStatus.isBlank()) {
			predicates.add(cb.equal(root.get("rentStatus"), rentStatus));
		}
		if (rentDate != null) {
			LocalDateTime startOfDay = rentDate.toLocalDate().atStartOfDay();
			LocalDateTime endOfDay = rentDate.toLocalDate().atTime(23, 59, 59, 999_999_999);
			Predicate startBeforeOrSame = cb.lessThanOrEqualTo(root.get("rentStime"), endOfDay);
			Predicate endAfterOrSame = cb.greaterThanOrEqualTo(root.get("rentEtime"), startOfDay);
			predicates.add(cb.and(startBeforeOrSame, endAfterOrSame));
		}
		return predicates;
	}
}
