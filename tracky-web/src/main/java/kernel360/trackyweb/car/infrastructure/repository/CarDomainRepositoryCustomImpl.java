package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.QBizEntity;
import kernel360.trackycore.core.common.entity.QCarEntity;
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
			.where(car.biz.id.eq(biz.id))
			.fetch();
	}

	//번호판 먼저
	@Override
	public Page<CarEntity> searchByFilter(String text, String status, Pageable pageable) {

		BooleanBuilder builder = searchByFilterBuilder(text, status, car);

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

	private BooleanBuilder searchByFilterBuilder(String text, String status, QCarEntity car) {
		BooleanBuilder builder = new BooleanBuilder();

		if (text != null && !text.isBlank()) {
			builder.and(
				car.mdn.containsIgnoreCase(text)
					.or(car.carPlate.containsIgnoreCase(text))
			);
		}

		if (status != null && !status.isBlank()) {
			builder.and(car.status.eq(status));
		}

		return builder;
	}

}


