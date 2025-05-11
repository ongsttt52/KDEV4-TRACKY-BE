package kernel360.trackyweb.admin.statistic.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminStatisticRequest;
import kernel360.trackyweb.admin.statistic.application.dto.response.GraphsResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.MonthlyDriveCountResponse;
import kernel360.trackyweb.admin.statistic.domain.AdminStatisticProvider;
import kernel360.trackyweb.biz.domain.provider.BizDomainProvider;
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

	public ApiResponse<List<AdminBizListResponse>> getAdminBizList() {

		List<AdminBizListResponse> bizList = adminStatisticProvider.getAdminBizList();

		return ApiResponse.success(AdminBizListResponse.addSum(bizList));
	}

	public ApiResponse<AdminBizStatisticResponse> getAdminBizStatistics(
		AdminStatisticRequest adminStatisticRequest) {

		Long bizId = mapToBizId(adminStatisticRequest.bizName());

		AdminBizStatisticResponse bizStat = adminStatisticProvider.getDriveStatByBizIdAndDate(
			bizId, adminStatisticRequest.selectedDate());

		return ApiResponse.success(bizStat);
	}

	public ApiResponse<List<MonthlyDriveCountResponse>> getAdminBizMonthlyDriveCount(String bizName) {

		Long bizId = mapToBizId(bizName);

		return ApiResponse.success(
			MonthlyDriveCountResponse.toDto(monthlyStatisticProvider.getAdminBizMonthlyDriveCount(bizId)));
	}

	public ApiResponse<List<HourlyGraphResponse>> getHourlyGraph() {
		return ApiResponse.success(HourlyGraphResponse.toResultList(timeDistanceDomainProvider.getYesterdayData()));
	}

	public ApiResponse<GraphsResponse> getGraphs() {
		List<GraphsResponse.CarCount> carCountWithBizName = dailyStatisticProvider.getCarCountWithBizName();
		List<GraphsResponse.OperationRate> operationRateWithBizName = dailyStatisticProvider.getOperationRatesAvgWithBizName();
		List<GraphsResponse.NonOperatedCar> nonOperatedCarWithBizName = monthlyStatisticProvider.getNonOperatedCarWithBizName();
		List<GraphsResponse.DriveCount> monthlyDriveCount = monthlyStatisticProvider.getDriveCount();

		return ApiResponse.success(GraphsResponse.toResponse(
			carCountWithBizName, operationRateWithBizName, nonOperatedCarWithBizName, monthlyDriveCount));
	}

	private Long mapToBizId(String bizName) {
		if (!bizName.isBlank()) {
			BizEntity biz = bizDomainProvider.getBizByBizName(bizName);
			return biz.getId();
		} else {
			return null;
		}
	}
}
