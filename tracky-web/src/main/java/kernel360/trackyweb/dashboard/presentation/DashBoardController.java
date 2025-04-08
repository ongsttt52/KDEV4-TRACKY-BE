package kernel360.trackyweb.dashboard.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.application.DashBoardService;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import kernel360.trackyweb.dashboard.domain.Statistics;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController implements DashBoardApiDocs {

	private final DashBoardService dashBoardService;

	@GetMapping("/rents/status")
	public ApiResponse<List<RentDashboardDto>> findRents(
		@RequestParam(name = "date", defaultValue = "today") String date
	) {
		return dashBoardService.findRents(date);
	}

	@GetMapping("/cars/status")
	public ApiResponse<Map<String, Long>> getAllCarStatus() {
		Map<String, Long> statusMap = dashBoardService.getAllCarStatus();
		return ApiResponse.success(statusMap);
	}

	@GetMapping("/statistics")
	public ApiResponse<Statistics> getStatistics() {
		return ApiResponse.success(dashBoardService.getStatistics());
	}
}
