package kernel360.trackyweb.realtime.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.realtime.application.dto.RealTimeService;
import kernel360.trackyweb.realtime.application.dto.request.RealTimeCarListRequest;
import kernel360.trackyweb.realtime.application.dto.response.LiveCarResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/realtime")
@RequiredArgsConstructor
public class RealTimeController {

	private final RealTimeService realTimeService;

	@GetMapping()
	public ApiResponse<List<LiveCarResponse>> getRunningCars(
		@ModelAttribute RealTimeCarListRequest realTimeCarListRequest
	) {
		return realTimeService.getRunningCars(realTimeCarListRequest);
	}

}
