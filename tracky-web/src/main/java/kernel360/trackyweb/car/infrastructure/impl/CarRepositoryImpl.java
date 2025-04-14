package kernel360.trackyweb.car.infrastructure.impl;

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
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackyweb.car.infrastructure.repo.CarRepositoryCustom;

@Repository
public class CarRepositoryImpl implements CarRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<CarEntity> searchByFilter(String mdn, String status, String purpose, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CarEntity> query = cb.createQuery(CarEntity.class);
		Root<CarEntity> car = query.from(CarEntity.class);

		List<Predicate> predicates = setPredicates(mdn, status, purpose, cb, car);
		query.where(cb.and(predicates.toArray(new Predicate[0])));

		// 정렬 처리
		if (pageable == null || !pageable.getSort().isSorted()) {
			query.orderBy(cb.desc(
				cb.selectCase()
					.when(cb.isNotNull(car.get("updatedAt")), car.get("updatedAt"))
					.otherwise(car.get("createdAt"))
			));
		}

		// 데이터 조회
		List<CarEntity> resultList;
		if (pageable != null) {
			resultList = em.createQuery(query)
				.setFirstResult((int)pageable.getOffset())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		} else {
			resultList = em.createQuery(query).getResultList();
		}

		// count 쿼리
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<CarEntity> countRoot = countQuery.from(CarEntity.class);
		List<Predicate> countPredicates = setPredicates(mdn, status, purpose, cb, countRoot);
		countQuery.select(cb.count(countRoot))
			.where(cb.and(countPredicates.toArray(new Predicate[0])));
		Long total = em.createQuery(countQuery).getSingleResult();

		return new PageImpl<>(resultList, pageable != null ? pageable : Pageable.unpaged(), total);
	}

	private List<Predicate> setPredicates(String mdn, String status, String purpose, CriteriaBuilder cb,
		Root<CarEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (mdn != null && !mdn.isBlank()) {
			predicates.add(cb.like(root.get("mdn"), "%" + mdn + "%"));
		}
		if (status != null && !status.isBlank()) {
			predicates.add(cb.equal(root.get("status"), status));
		}
		if (purpose != null && !purpose.isBlank()) {
			predicates.add(cb.equal(root.get("purpose"), purpose));
		}
		return predicates;
	}

}
