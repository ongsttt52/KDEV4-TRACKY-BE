package kernel360.trackyweb.dashboard.presentation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.application.DashBoardService;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController implements DashBoardApiDocs {

	private final DashBoardService dashBoardService;

	@GetMapping("/rents")
	@Override
	public ApiResponse<List<RentDashboardDto>> findRents(
		@RequestParam(name = "date", defaultValue = "today") String date
	) {
		return dashBoardService.findRents(date);
	}
}