package kernel360.trackyweb.admin.statistic.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.admin.statistic.application.AdminStatisticService;
import kernel360.trackyweb.admin.statistic.application.dto.request.AdminStatisticRequest;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizMonthlyResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminGraphStatsResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/statistic")
@RequiredArgsConstructor
public class AdminStatisticController {

	private final AdminStatisticService adminStatisticService;

	@GetMapping("/biz")
	public ApiResponse<List<AdminBizListResponse>> getAdminBizList() {
		return adminStatisticService.getAdminBizList();
	}

	@GetMapping("/biz/stat")
	public ApiResponse<AdminBizStatisticResponse> getAdminBizStatistics(
		@ModelAttribute AdminStatisticRequest adminStatisticRequest
	) {
		return adminStatisticService.getAdminBizStatistics(adminStatisticRequest);
	}

	@GetMapping("/monthly")
	public ApiResponse<List<AdminBizMonthlyResponse>> getMonthlyDriveCounts(
		@ModelAttribute AdminStatisticRequest adminStatisticRequest
	) {
		return adminStatisticService.getMonthlyDriveCounts(adminStatisticRequest);
	}

	@GetMapping("/hourly")
	public ApiResponse<List<HourlyGraphResponse>> getHourlyDriveCounts(
		@ModelAttribute AdminStatisticRequest adminStatisticRequest
	) {
		return adminStatisticService.getHourlyDriveCounts(adminStatisticRequest);
	}

	@GetMapping("/graphs")
	public ApiResponse<AdminGraphStatsResponse> getGraphStats() {
		return adminStatisticService.getGraphStats();
	}
}
