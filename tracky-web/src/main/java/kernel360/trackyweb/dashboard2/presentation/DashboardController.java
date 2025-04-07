package kernel360.trackyweb.dashboard2.presentation;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard2.application.DashboardCarDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardCarDataService dashboardCarDataService;

	@GetMapping("/cars/status")
	public ApiResponse<Map<String, Long>> getAllCarStatus() {
		Map<String, Long> statusMap = dashboardCarDataService.getAllCarStatus();
		return ApiResponse.success(statusMap);
	}

}
