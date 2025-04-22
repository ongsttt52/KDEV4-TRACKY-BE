package kernel360.trackyweb.car.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QCarEntity.*;

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
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarDomainRepositoryCustomImpl implements CarDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<String> findAllMdnByBizId(Long bizId) {
		return queryFactory
			.select(carEntity.mdn)
			.from(carEntity)
			.where(carEntity.biz.id.eq(bizId))
			.fetch();
	}

	@Override

	public Page<CarEntity> searchByFilter(String search, String status, String carType, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder()
			.and(isContainsCarMdnOrCarPlate(search))
			.and(isContainsCarStatus(status))
			.and(isContainsCarType(carType));

		JPAQuery<CarEntity> query = queryFactory
			.selectFrom(carEntity)
			.where(builder)
			.orderBy(carPlateSort(search));


	private long countByFilter(BooleanBuilder builder) {
		return Optional.ofNullable(
			queryFactory
				.select(carEntity.count())
				.from(carEntity)
				.where(builder)
				.fetchOne()
		).orElse(0L);
	}

	//검색 조건
	private BooleanExpression isContainsCarMdnOrCarPlate(String search) {
		if (StringUtils.isBlank(search)) {
			return null;
		}
		return carEntity.mdn.containsIgnoreCase(search)
			.or(carEntity.carPlate.containsIgnoreCase(search));
	}

	private BooleanExpression isContainsCarStatus(String status) {
		if (StringUtils.isBlank(status)) {
			return null;
		}
		return carEntity.status.eq(status);
	}

	private BooleanExpression isContainsCarType(String carType) {
		if (StringUtils.isBlank(carType)) {
			return null;
		}
		return carEntity.carType.eq(carType);
	}

	//정렬 조건
	private OrderSpecifier<?>[] carPlateSort(String search) {
		if (StringUtils.isNotBlank(search)) {
			NumberTemplate<Integer> carPlatePriority = Expressions.numberTemplate(
				Integer.class, "CASE WHEN {1} LIKE CONCAT('%', {0}, '%') THEN 0 ELSE 1 END",
				search, carEntity.carPlate
			);

			NumberTemplate<Integer> carPlatePos = Expressions.numberTemplate(
				Integer.class, "LOCATE({0}, {1})", search, carEntity.carPlate
			);

			return new OrderSpecifier<?>[] {
				carPlatePriority.asc(),
				carPlatePos.asc(),
				carEntity.carPlate.asc()
			};
		}
		return new OrderSpecifier<?>[] {carEntity.carPlate.asc()};
	}
}


