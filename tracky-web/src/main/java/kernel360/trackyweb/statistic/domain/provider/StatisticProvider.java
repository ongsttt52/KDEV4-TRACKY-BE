package kernel360.trackyweb.statistic.domain.provider;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.statistic.infrastructure.repository.DailyStatisticDomainRepository;
import kernel360.trackyweb.statistic.infrastructure.repository.MonthlyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticProvider {

	private final DailyStatisticDomainRepository dailyStatisticRepository;
	private final MonthlyStatisticDomainRepository monthlyStatisticRepository;

	public DailyStatisticEntity getDailyStatistic(String bizUuid, LocalDate date) {

		return dailyStatisticRepository.findByBizUuidAndDate(bizUuid, date)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.STATISTIC_NOT_FOUND));
	}

	public MonthlyStatisticEntity getMonthlyStatistic(String bizUuid, LocalDate date) {

		return monthlyStatisticRepository.findByBizUuidAndDate(bizUuid, date)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.STATISTIC_NOT_FOUND));
	}
}
