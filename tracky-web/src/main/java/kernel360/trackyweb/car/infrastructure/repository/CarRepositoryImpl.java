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
			predicates.add(cb.like(car.get("mdn"), "%" + mdn + "%"));
		}
		if (status != null && !status.isBlank()) {
			predicates.add(cb.equal(car.get("status"), status));
		}
		if (purpose != null && !purpose.isBlank()) {
			predicates.add(cb.equal(car.get("purpose"), purpose));
		}
		query.where(cb.and(predicates.toArray(new Predicate[0])));
		if (mdn != null && !mdn.isBlank()) {
			query.orderBy(cb.asc(cb.locate(car.get("mdn"), mdn)));
		}
		List<CarEntity> resultList = em.createQuery(query)
			.setFirstResult((int)pageable.getOffset())
			.setMaxResults(pageable.getPageSize())
			.getResultList();

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<CarEntity> countRoot = countQuery.from(CarEntity.class);
		List<Predicate> countPredicates = new ArrayList<>();
		if (mdn != null && !mdn.isBlank()) {
			countPredicates.add(cb.like(countRoot.get("mdn"), "%" + mdn + "%"));
		}
		if (status != null && !status.isBlank()) {
			countPredicates.add(cb.equal(countRoot.get("status"), status));
		}
		if (purpose != null && !purpose.isBlank()) {
			countPredicates.add(cb.equal(countRoot.get("purpose"), purpose));
		}
		countQuery.select(cb.count(countRoot))
			.where(cb.and(countPredicates.toArray(new Predicate[0])));
		Long total = em.createQuery(countQuery).getSingleResult();

		return new PageImpl<>(resultList, pageable, total);
	}
}
