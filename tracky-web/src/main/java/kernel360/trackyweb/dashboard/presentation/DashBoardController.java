package kernel360.trackyweb.dashboard.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.application.DashBoardService;
import kernel360.trackyweb.dashboard.application.dto.response.DashboardCarStatusResponse;
import kernel360.trackyweb.dashboard.application.dto.response.DashboardReturnResponse;
import kernel360.trackyweb.dashboard.application.dto.response.DashboardStatisticsResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController implements DashBoardApiDocs {

	private final DashBoardService dashBoardService;

	@GetMapping("/return/status")
	public ApiResponse<List<DashboardReturnResponse>> getdelayedReturn(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
		return dashBoardService.getDelayedReturn(memberPrincipal.bizUuid());
	}

	@GetMapping("/cars/status")
	public ApiResponse<List<DashboardCarStatusResponse>> getAllCarStatus(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	) {
		return ApiResponse.success(dashBoardService.getAllCarStatus(memberPrincipal.bizUuid()));
	}

	@GetMapping("/statistics")
	public ApiResponse<DashboardStatisticsResponse> getStatistics(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
		return ApiResponse.success(dashBoardService.getStatistics(memberPrincipal.bizUuid()));
	}

	@PatchMapping("/return/status/{rentUuid}")
	public ApiResponse<String> updateStatusToReturn(@PathVariable String rentUuid) {
		return dashBoardService.updateStatusToReturn(rentUuid);
	}

	@GetMapping("/geo")
	public ApiResponse<Map<String, List<String>>> getGeoData(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	) {
		Map<String, List<String>> geoMap = dashBoardService.getGeoData(memberPrincipal.bizUuid());
		return ApiResponse.success(geoMap);
	}
}
