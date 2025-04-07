package kernel360.trackyweb.car.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kernel360.trackycore.core.common.entity.CarEntity;

public class CarRepositoryImpl implements CarRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<CarEntity> searchByFilter(String mdn, String status, String purpose, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CarEntity> query = cb.createQuery(CarEntity.class);
		Root<CarEntity> car = query.from(CarEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		if (mdn != null && !mdn.isBlank()) {
			// mdn 부분 일치 (LIKE) 조건 추가
			predicates.add(cb.like(car.get("mdn"), "%" + mdn + "%"));
		}
		if (status != null && !status.isBlank()) {
			predicates.add(cb.equal(car.get("status"), status));
		}
		if (purpose != null && !purpose.isBlank()) {
			predicates.add(cb.equal(car.get("purpose"), purpose));
		}

		query.where(cb.and(predicates.toArray(new Predicate[0])));

		// mdn 정렬: 검색어가 앞쪽에 있을수록 우선
		if (mdn != null && !mdn.isBlank()) {
			// LOCATE(mdn, '검색어') ASC 정렬
			query.orderBy(cb.asc(cb.locate(car.get("mdn"), mdn)));
		}

		List<CarEntity> resultList = em.createQuery(query)
			.setFirstResult((int)pageable.getOffset())
			.setMaxResults(pageable.getPageSize())
			.getResultList();

		// 전체 건수를 위한 Count 쿼리 생성
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<CarEntity> countRoot = countQuery.from(CarEntity.class);
		countQuery.select(cb.count(countRoot))
			.where(cb.and(predicates.toArray(new Predicate[0])));
		Long total = em.createQuery(countQuery).getSingleResult();

		return new PageImpl<>(resultList, pageable, total);
	}

}
