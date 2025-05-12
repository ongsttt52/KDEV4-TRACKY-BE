package kernel360.trackyweb.admin.statistic.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackyweb.admin.statistic.application.dto.request.AdminStatisticRequest;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizMonthlyResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminGraphStatsResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import kernel360.trackyweb.admin.statistic.domain.AdminStatisticProvider;
import kernel360.trackyweb.biz.domain.provider.BizDomainProvider;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.statistic.domain.provider.DailyStatisticProvider;
import kernel360.trackyweb.statistic.domain.provider.MonthlyStatisticProvider;
import kernel360.trackyweb.timedistance.domain.provider.TimeDistanceDomainProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStatisticService {

	private final AdminStatisticProvider adminStatisticProvider;
	private final BizDomainProvider bizDomainProvider;
	private final TimeDistanceDomainProvider timeDistanceDomainProvider;
	private final DailyStatisticProvider dailyStatisticProvider;
	private final MonthlyStatisticProvider monthlyStatisticProvider;
	private final CarDomainProvider carDomainProvider;

	public ApiResponse<List<AdminBizListResponse>> getAdminBizList() {

		List<AdminBizListResponse> bizList = adminStatisticProvider.getAdminBizList();

		return ApiResponse.success(AdminBizListResponse.addSum(bizList));
	}

	public ApiResponse<AdminBizStatisticResponse> getAdminBizStatistics(
		AdminStatisticRequest adminStatisticRequest
	) {
		Long bizId = mapToBizId(adminStatisticRequest.bizName());

		AdminBizStatisticResponse bizStat = adminStatisticProvider.getDriveStatByBizIdAndDate(bizId,
			adminStatisticRequest.selectedDate());

		return ApiResponse.success(bizStat);
	}

	public ApiResponse<List<AdminBizMonthlyResponse>> getMonthlyDriveCounts(
		AdminStatisticRequest adminStatisticRequest
	) {
		Long bizId = mapToBizId(adminStatisticRequest.bizName());

		return ApiResponse.success(adminStatisticProvider
			.getAdminBizMonthlyDriveCount(bizId, adminStatisticRequest.selectedDate()));
	}

	public ApiResponse<List<HourlyGraphResponse>> getHourlyDriveCounts(
		AdminStatisticRequest adminStatisticRequest
	) {
		Long bizId = mapToBizId(adminStatisticRequest.bizName());

		return ApiResponse.success(HourlyGraphResponse
			.toResultList(adminStatisticProvider
				.getAdminBizHourlyDriveCount(bizId, adminStatisticRequest.selectedDate())));
	}

	public ApiResponse<AdminGraphStatsResponse> getGraphStats() {

		List<AdminGraphStatsResponse.CarCount> carCountWithBizName = dailyStatisticProvider.getCarCountWithBizName();
		List<AdminGraphStatsResponse.CarTypeCount> carTypeCount = carDomainProvider.getCarTypeCounts();
		List<AdminGraphStatsResponse.OperationRate> operationRateWithBizName = dailyStatisticProvider.getOperationRatesAvgWithBizName();
		List<AdminGraphStatsResponse.NonOperatedCar> nonOperatedCarWithBizName = monthlyStatisticProvider.getNonOperatedCarWithBizName();

		return ApiResponse.success(AdminGraphStatsResponse
			.toResponse(carCountWithBizName, carTypeCount, operationRateWithBizName, nonOperatedCarWithBizName));
	}

	private Long mapToBizId(String bizName) {

		if (bizName.isBlank()) {
			return null;
		}
		BizEntity biz = bizDomainProvider.getBizByBizName(bizName);
		return biz.getId();
	}
}
