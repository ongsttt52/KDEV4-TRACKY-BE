package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
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
		QBizEntity biz = QBizEntity.bizEntity;

		return queryFactory
			.select(car.mdn)
			.from(car)
			.where(car.biz.id.eq(bizId))
			.fetch();
	}

	//번호판 먼저
	@Override
	public Page<CarEntity> searchByFilter(String search, String status, Pageable pageable) {

		BooleanBuilder builder = searchByFilterBuilder(search, status, car);

		JPAQuery<CarEntity> query = queryFactory
			.selectFrom(car)
			.where(builder);

		// 검색어가 있을 때 정렬 로직 추가
		if (search != null && !search.isBlank()) {
			// 포함 여부: carPlate에 검색어 포함 → 우선순위로 사용
			NumberTemplate<Integer> carPlatePriority = Expressions.numberTemplate(
				Integer.class, "CASE WHEN {1} LIKE CONCAT('%', {0}, '%') THEN 0 ELSE 1 END",
				search, car.carPlate
			);

			// 포함 위치: 포함된 경우, 앞에 있을수록 우선
			NumberTemplate<Integer> carPlatePos = Expressions.numberTemplate(
				Integer.class, "LOCATE({0}, {1})", search, car.carPlate
			);

			query.orderBy(
				carPlatePriority.asc(),   // 0 (carPlate 포함) 먼저
				carPlatePos.asc(),        // 앞쪽 위치 먼저
				car.carPlate.asc()        // 그 다음 carPlate 순서
			);
		} else {
			query.orderBy(car.carPlate.asc()); // 검색어 없으면 단순 carPlate 정렬
		}

		List<CarEntity> content = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = Optional.ofNullable(
			queryFactory
				.select(car.count())
				.from(car)
				.where(builder)
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanBuilder searchByFilterBuilder(String search, String status, QCarEntity car) {
		BooleanBuilder builder = new BooleanBuilder();

		if (search != null && !search.isBlank()) {
			builder.and(
				car.mdn.containsIgnoreCase(search)
					.or(car.carPlate.containsIgnoreCase(search))
			);
		}

		if (status != null && !status.isBlank()) {
			builder.and(car.status.eq(status));
		}

		return builder;
	}

}


