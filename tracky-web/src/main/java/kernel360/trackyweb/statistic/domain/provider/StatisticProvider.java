package kernel360.trackyweb.statistic.domain.provider;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticProvider {

	public DailyStatisticEntity getDailyStatistic(String bizUuid, LocalDate date) {
		return DailyStatisticEntity.create(bizUuid, date, 1, 2, 3.5, 4L, 5, 6.7);
	}

	public MonthlyStatisticEntity getMonthlyStatistic(String bizUuid, LocalDate date) {
		return MonthlyStatisticEntity.create(bizUuid, date, 1, 2, 3.5, 4L, 5, 6.7);
	}
}
