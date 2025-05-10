package kernel360.trackyweb.timedistance.infrastructure.repository;

import static kernel360.trackycore.core.domain.entity.QDriveEntity.*;
import static kernel360.trackycore.core.domain.entity.QTimeDistanceEntity.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackyweb.admin.statistic.application.dto.AdminBizStatisticResponse;
import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeDistanceDomainRepositoryCustomImpl implements TimeDistanceDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<OperationTime> getTotalOperationTimeGroupedByBIzId(LocalDate date) {
		return List.of();
	}

	@Override
	public List<Tuple> countByBizIdAndDateGroupedByHour(Long bizId, LocalDate date) {

		return queryFactory.select(timeDistanceEntity.hour, timeDistanceEntity.count())
			.from(timeDistanceEntity)
			.where(timeDistanceEntity.biz.id.eq(bizId)
				.and(timeDistanceEntity.date.eq(date))
			)
			.groupBy(timeDistanceEntity.hour)
			.orderBy(timeDistanceEntity.hour.asc())
			.fetch();
	}

	@Override
	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate) {

		return queryFactory
			.select(Projections.constructor(
				AdminBizStatisticResponse.class,
				driveEntity.id.countDistinct().intValue(),
				timeDistanceEntity.distance.sum(),
				timeDistanceEntity.seconds.sum(),
				Expressions.nullExpression(List.class)
			))
			.from(timeDistanceEntity)
			.join(driveEntity)
			.on(driveEntity.car.mdn.eq(timeDistanceEntity.car.mdn))
			.where(isEqualsBizId(bizId)
				.and(timeDistanceEntity.date.eq(selectedDate))
			)
			.groupBy(timeDistanceEntity.biz.id)
			.fetchOne();
	}

	private BooleanExpression isEqualsBizId(Long bizId) {
		if (bizId == null) {
			return Expressions.TRUE;
		}
		return timeDistanceEntity.biz.id.eq(bizId);
	}
}