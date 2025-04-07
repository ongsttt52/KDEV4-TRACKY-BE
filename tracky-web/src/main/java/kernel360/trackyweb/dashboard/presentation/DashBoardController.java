package kernel360.trackyweb.dashboard.presentation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

	@GetMapping("/rents")
	// public ApiResponse<List<RentDashboardDto>> getRents(
	public ApiResponse<String> getRents(
		@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
	) {
		// List<RentDashboardDto> rents = dashboardService.getRentsByDate(date);
		return ApiResponse.success("data");
	}
}
