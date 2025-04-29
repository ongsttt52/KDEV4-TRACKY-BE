package kernel360.trackyweb.statistic.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.statistic.application.dto.response.DailyStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import kernel360.trackyweb.statistic.domain.provider.StatisticProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService {

	private final StatisticProvider statisticProvider;

	public ApiResponse<DailyStatisticResponse> getDailyStatistic(String bizUuid, LocalDate date) {

		DailyStatisticEntity dailyStatistic = statisticProvider.getDailyStatistic(bizUuid, date);

		DailyStatisticResponse response = DailyStatisticResponse.from(dailyStatistic, list);

		return ApiResponse.success(response);
	}

	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(String bizUuid, LocalDate date) {

		MonthlyStatisticEntity monthlyStatistic = statisticProvider.getMonthlyStatistic(bizUuid, date);

		MonthlyStatisticResponse response = MonthlyStatisticResponse.from(monthlyStatistic, list, list2);

		return ApiResponse.success(response);
	}

}