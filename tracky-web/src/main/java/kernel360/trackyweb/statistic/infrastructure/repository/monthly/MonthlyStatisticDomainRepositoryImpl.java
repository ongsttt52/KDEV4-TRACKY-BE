package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kernel360.trackycore.core.domain.entity.QMonthlyStatisticEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MonthlyStatisticDomainRepositoryImpl implements MonthlyStatisticDomainRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Tuple> getMonthlyDataTuples(Long bizId, LocalDate currentDate, LocalDate targetDate) {
		QMonthlyStatisticEntity e = QMonthlyStatisticEntity.monthlyStatisticEntity;

		return queryFactory.select(e.date.year(), e.date.month(), e.totalDriveCount.sum(), e.totalDriveDistance.sum())
			.from(e)
			.where(
				e.bizId.eq(bizId)
					.and(e.date.between(targetDate, currentDate))
			)
			.groupBy(e.date.yearMonth())
			.orderBy(e.date.yearMonth().asc())
			.fetch();
	}
}
