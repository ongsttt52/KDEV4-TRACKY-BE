package kernel360.trackyweb.admin.statistic.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.admin.statistic.application.AdminStatisticService;
import kernel360.trackyweb.admin.statistic.application.dto.AdminStatisticResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/statistic")
@RequiredArgsConstructor
public class AdminStatisticController {

	private final AdminStatisticService adminStatisticService;

	@GetMapping()
	public ApiResponse<AdminStatisticResponse> getAdminStatistic() {

		return adminStatisticService.getAdminStatistic();
	}

}
