package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.QBizEntity;
import kernel360.trackycore.core.domain.entity.QCarEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarDomainRepositoryCustomImpl implements CarDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final QCarEntity car = QCarEntity.carEntity;

	@Override
	public List<String> findAllMdnByBizId(Long bizId) {

		return queryFactory
			.select(car.mdn)
			.from(car)
			.where(car.biz.id.eq(bizId))
			.fetch();
	}

	//번호판 먼저
	@Override
	public Page<CarEntity> searchByFilter(String search, String status, Pageable pageable) {

		BooleanBuilder builder = new BooleanBuilder()
			.and(searchContains(search))
			.and(statusEquals(status));

		JPAQuery<CarEntity> query = queryFactory
			.selectFrom(car)
			.where(builder)
			.orderBy(carPlateSort(search));

		List<CarEntity> content = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = countByFilter(builder);

		return new PageImpl<>(content, pageable, total);
	}


	private OrderSpecifier<?>[] carPlateSort(String search) {
		if (StringUtils.isNotBlank(search)) {
			NumberTemplate<Integer> carPlatePriority = Expressions.numberTemplate(
				Integer.class, "CASE WHEN {1} LIKE CONCAT('%', {0}, '%') THEN 0 ELSE 1 END",
				search, car.carPlate
			);

			NumberTemplate<Integer> carPlatePos = Expressions.numberTemplate(
				Integer.class, "LOCATE({0}, {1})", search, car.carPlate
			);

			return new OrderSpecifier<?>[] {
				carPlatePriority.asc(),
				carPlatePos.asc(),
				car.carPlate.asc()
			};
		}
		return new OrderSpecifier<?>[] { car.carPlate.asc() };
	}

	private long countByFilter(BooleanBuilder builder) {
		return Optional.ofNullable(
			queryFactory
				.select(car.count())
				.from(car)
				.where(builder)
				.fetchOne()
		).orElse(0L);
	}

	public BooleanExpression searchContains(String search) {
		if (StringUtils.isNotBlank(search)) {
			return car.mdn.containsIgnoreCase(search)
				.or(car.carPlate.containsIgnoreCase(search));
		}
		return null;
	}

	public BooleanExpression statusEquals(String status) {
		if (StringUtils.isNotBlank(status)) {
			return car.status.eq(status);
		}
		return null;
	}
}


