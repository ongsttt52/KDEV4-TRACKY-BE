package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
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

		List<CarEntity> content = queryFactory
			.selectFrom(car)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(car.carPlate.asc())
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


