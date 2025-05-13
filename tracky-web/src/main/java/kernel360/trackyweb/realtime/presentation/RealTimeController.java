package kernel360.trackyweb.realtime.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.realtime.application.dto.RealTimeService;
import kernel360.trackyweb.realtime.application.dto.request.RealTimeCarListRequest;
import kernel360.trackyweb.realtime.application.dto.response.GpsDataResponse;
import kernel360.trackyweb.realtime.application.dto.response.RunningCarDetailResponse;
import kernel360.trackyweb.realtime.application.dto.response.RunningCarResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/realtime")
@RequiredArgsConstructor
public class RealTimeController {

	private final RealTimeService realTimeService;

	@GetMapping()
	public ApiResponse<List<RunningCarResponse>> getRunningCars(
		@ModelAttribute RealTimeCarListRequest realTimeCarListRequest,
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	) {
		return realTimeService.getRunningCars(memberPrincipal.bizUuid(), realTimeCarListRequest);
	}

	@GetMapping("/{id}")
	public ApiResponse<RunningCarDetailResponse> getRunningCarDetail(@PathVariable Long id) {
		return realTimeService.getRunningCarDetailById(id);
	}

	@GetMapping("/gps/beforepath/{id}")
	public ApiResponse<List<GpsDataResponse>> getBeforeGpsPath(
		@PathVariable Long id,
		@RequestParam("nowTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime nowTime
	) {
		return ApiResponse.success(realTimeService.getBeforeGpsPath(id, nowTime));
	}

}
