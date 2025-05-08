package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import static kernel360.trackycore.core.domain.entity.QMonthlyStatisticEntity.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MonthlyStatisticRepositoryCustomImpl implements MonthlyStatisticRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public MonthlyStatisticEntity findLatestMonthlyStatistic(String bizUuid) {
		return queryFactory
			.selectFrom(monthlyStatisticEntity)
			.where(monthlyStatisticEntity.biz.bizUuid.eq(bizUuid))
			.orderBy(monthlyStatisticEntity.date.desc())
			.limit(1)
			.fetchOne();
	}
}
