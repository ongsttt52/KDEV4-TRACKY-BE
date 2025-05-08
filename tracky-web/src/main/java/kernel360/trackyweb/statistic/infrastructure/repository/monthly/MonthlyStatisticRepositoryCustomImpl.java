package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import static kernel360.trackycore.core.domain.entity.QMonthlyStatisticEntity.*;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackyweb.statistic.application.dto.internal.DashboardStatistic;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MonthlyStatisticRepositoryCustomImpl implements MonthlyStatisticRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public DashboardStatistic findStatisticReportByBizUuid(String bizUuid) {
		return queryFactory
			.select(Projections.constructor(
				DashboardStatistic.class,
				monthlyStatisticEntity.avgOperationRate,
				monthlyStatisticEntity.nonOperatingCarCount,
				monthlyStatisticEntity.totalDriveCount
			))
			.from(monthlyStatisticEntity)
			.where(monthlyStatisticEntity.biz.bizUuid.eq(bizUuid))
			.orderBy(monthlyStatisticEntity.date.desc())
			.limit(1)
			.fetchOne();
	}
}
