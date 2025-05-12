package kernel360.trackyweb.statistic.application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackycore.core.domain.provider.BizProvider;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.statistic.application.dto.request.CarStatisticRequest;
import kernel360.trackyweb.statistic.application.dto.response.CarStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.DailyStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import kernel360.trackyweb.statistic.domain.provider.DailyStatisticProvider;
import kernel360.trackyweb.statistic.domain.provider.MonthlyStatisticProvider;
import kernel360.trackyweb.timedistance.domain.provider.TimeDistanceDomainProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService {

	private final DailyStatisticProvider dailyStatisticProvider;
	private final MonthlyStatisticProvider monthlyStatisticProvider;
	private final TimeDistanceDomainProvider timeDistanceDomainProvider;
	private final BizProvider bizProvider;
	private final CarDomainProvider carDomainProvider;

	public ApiResponse<DailyStatisticResponse> getDailyStatistic(String bizUuid, LocalDate date) {

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		DailyStatisticEntity dailyStatistic = dailyStatisticProvider.getDailyStatistic(bizId, date);

		long[] hourlyDriveCounts = timeDistanceDomainProvider.getHourlyDriveCounts(bizId, date);

		DailyStatisticResponse response = DailyStatisticResponse.from(dailyStatistic, hourlyDriveCounts);

		return ApiResponse.success(response);
	}

	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(String bizUuid, YearMonth date,
		YearMonth targetDate) {

		if (targetDate == null) {
			targetDate = date.minusMonths(11);
		}

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		MonthlyStatisticEntity monthlyStatistic = monthlyStatisticProvider.getMonthlyStatistic(bizId, date.atDay(1));

		List<MonthlyStatisticResponse.MonthlyStats> monthlyStats = monthlyStatisticProvider.getMonthlyStats(bizId,
			date.atDay(1),
			targetDate.atDay(1));

		MonthlyStatisticResponse response = MonthlyStatisticResponse.from(monthlyStatistic, monthlyStats);

		return ApiResponse.success(response);
	}

	public ApiResponse<List<CarStatisticResponse>> getCarStatistic(String bizUuid,
		CarStatisticRequest carStatisticRequest) {

		Long bizId = bizProvider.getBiz(bizUuid).getId();

		Page<CarStatisticResponse> carResponses = carDomainProvider.searchCarStatisticByFilter(
			bizId,
			carStatisticRequest.search(),
			carStatisticRequest.toPageable());

		PageResponse pageResponse = PageResponse.from(carResponses);

		return ApiResponse.success(carResponses.getContent(), pageResponse);
	}
}