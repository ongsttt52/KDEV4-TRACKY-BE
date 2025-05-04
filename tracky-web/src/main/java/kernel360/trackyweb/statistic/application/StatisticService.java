package kernel360.trackyweb.statistic.application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackycore.core.domain.provider.BizProvider;
import kernel360.trackyweb.statistic.application.dto.response.DailyStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import kernel360.trackyweb.statistic.domain.provider.DailyStatisticProvider;
import kernel360.trackyweb.statistic.domain.provider.MonthlyStatisticProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService {

	private final DailyStatisticProvider dailyStatisticProvider;
	private final MonthlyStatisticProvider monthlyStatisticProvider;
	private final BizProvider bizProvider;

	public ApiResponse<DailyStatisticResponse> getDailyStatistic(String bizUuid, LocalDate date) {

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		DailyStatisticEntity dailyStatistic = dailyStatisticProvider.getDailyStatistic(bizId, date);

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			list.add(1);
		}
		DailyStatisticResponse response = DailyStatisticResponse.from(dailyStatistic, list);

		return ApiResponse.success(response);
	}

	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(String bizUuid, YearMonth date) {

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		LocalDate localDate = date.atDay(1);

		MonthlyStatisticEntity monthlyStatistic = monthlyStatisticProvider.getMonthlyStatistic(bizId, localDate);

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