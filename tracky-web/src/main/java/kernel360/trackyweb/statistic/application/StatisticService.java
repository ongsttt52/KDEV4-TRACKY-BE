package kernel360.trackyweb.statistic.application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.domain.provider.BizProvider;
import kernel360.trackyweb.statistic.domain.dto.MonthlyStat;
import kernel360.trackyweb.statistic.domain.dto.Summary;
import kernel360.trackyweb.statistic.domain.provider.DailyStatisticProvider;
import kernel360.trackyweb.statistic.domain.provider.MonthlyStatisticProvider;
import kernel360.trackyweb.statistic.presentation.dto.DailyStatisticResponse;
import kernel360.trackyweb.statistic.presentation.dto.MonthlyStatisticResponse;
import kernel360.trackyweb.timedistance.domain.provider.TimeDistanceDomainProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService {

	private final DailyStatisticProvider dailyStatisticProvider;
	private final MonthlyStatisticProvider monthlyStatisticProvider;
	private final TimeDistanceDomainProvider timeDistanceDomainProvider;
	private final BizProvider bizProvider;

	public ApiResponse<DailyStatisticResponse> getDailyStatistic(String bizUuid, LocalDate date) {

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		DailyStatisticEntity dailyStatistic = dailyStatisticProvider.getDailyStatistic(bizId, date);

		long[] hourlyDriveCounts = timeDistanceDomainProvider.getHourlyDriveCounts(bizId, date);

		DailyStatisticResponse response = DailyStatisticResponse.from(dailyStatistic, hourlyDriveCounts);

		return ApiResponse.success(response);
	}

	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(String bizUuid, YearMonth date,
		YearMonth targetDate) {

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		Summary summary = monthlyStatisticProvider.getSummary(bizId, date);

		List<MonthlyStat> monthlyStats = monthlyStatisticProvider.getMonthlyStats(bizId, date, targetDate);

		MonthlyStatisticResponse response = new MonthlyStatisticResponse(summary, monthlyStats);

		return ApiResponse.success(response);
	}
}