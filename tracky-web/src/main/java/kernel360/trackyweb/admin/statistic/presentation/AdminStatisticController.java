package kernel360.trackyweb.admin.statistic.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.admin.statistic.application.AdminStatisticService;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.AdminStatisticRequest;
import kernel360.trackyweb.admin.statistic.application.dto.response.GraphsResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.MonthlyDriveCountResponse;
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
		@ModelAttribute AdminStatisticRequest adminStatisticRequest) {
		return adminStatisticService.getAdminBizStatistics(adminStatisticRequest);
	}

	@GetMapping("/monthly")
	public ApiResponse<List<MonthlyDriveCountResponse>> getMonthlyDriveCounts(
		@RequestParam String bizName) {
		return adminStatisticService.getAdminBizMonthlyDriveCount(bizName);
	}

	@GetMapping("/hourly")
	public ApiResponse<List<HourlyGraphResponse>> getHourlyDriveCounts() {
		return adminStatisticService.getHourlyGraph();
	}

	@GetMapping("/graphs")
	public ApiResponse<GraphsResponse> getGraphs() {
		return adminStatisticService.getGraphs();
	}
}
