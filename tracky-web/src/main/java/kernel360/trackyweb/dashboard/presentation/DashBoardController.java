package kernel360.trackyweb.dashboard.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackyweb.dashboard.application.DashBoardService;
import kernel360.trackyweb.dashboard.application.dto.response.ReturnResponse;
import kernel360.trackyweb.dashboard.domain.Statistics;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController implements DashBoardApiDocs {

	private final DashBoardService dashBoardService;

	@GetMapping("/return/status")
	public ApiResponse<List<ReturnResponse>> getdelayedReturn(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
		return dashBoardService.getDelayedReturn(memberPrincipal.bizUuid());
	}

	@GetMapping("/cars/status")
	public ApiResponse<Map<CarStatus, Long>> getAllCarStatus() {
		Map<CarStatus, Long> statusMap = dashBoardService.getAllCarStatus();
		return ApiResponse.success(statusMap);
	}

	@GetMapping("/statistics")
	public ApiResponse<Statistics> getStatistics() {
		return ApiResponse.success(dashBoardService.getStatistics());
	}
/*
	@GetMapping("/geo")
	public ApiResponse<Map<String, Integer>> getGeoData() {
		Map<String, Integer> geoMap = dashBoardService.getGeoData();
		return ApiResponse.success(geoMap);
	}*/
}
