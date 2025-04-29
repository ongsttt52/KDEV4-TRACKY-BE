package kernel360.trackyweb.statistic.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	public ApiResponse<DailyStatisticResponse> getDailyStatistic(UUID bizUuid, LocalDate date) {

		DailyStatisticEntity dailyStatistic = statisticProvider.getDailyStatistic(bizUuid, date);

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			list.add(1);
		}
		DailyStatisticResponse response = DailyStatisticResponse.from(dailyStatistic, list);

		return ApiResponse.success(response);
	}

	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(UUID bizUuid, LocalDate date) {

		MonthlyStatisticEntity monthlyStatistic = statisticProvider.getMonthlyStatistic(bizUuid, date);

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			list.add(1);
		}
		List<Long> list2 = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			list2.add(1L);
		}
		MonthlyStatisticResponse response = MonthlyStatisticResponse.from(monthlyStatistic, list, list2);

		return ApiResponse.success(response);
	}

}