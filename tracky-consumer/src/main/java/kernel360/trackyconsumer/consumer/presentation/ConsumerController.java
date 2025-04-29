package kernel360.trackyconsumer.consumer.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyconsumer.consumer.application.dto.request.CarOnOffRequest;
import kernel360.trackyconsumer.consumer.application.dto.request.GpsHistoryMessage;
import kernel360.trackyconsumer.consumer.application.dto.response.MdnBizResponse;
import kernel360.trackyconsumer.consumer.application.service.ConsumerService;
import kernel360.trackycore.core.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/consume")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

	private final ConsumerService ConsumerService;

	@PostMapping("/on")
	public ApiResponse<String> postCarOn(@RequestBody CarOnOffRequest request) {

		ConsumerService.processOnMessage(request);
		return ApiResponse.success(request.mdn());
	}

	@PostMapping("/off")
	public ApiResponse<String> postCarOff(@RequestBody CarOnOffRequest request) {

		ConsumerService.processOffMessage(request);
		return ApiResponse.success(request.mdn());
	}

	@PostMapping("/cycle")
	public ApiResponse<String> postCarCycle(@RequestBody GpsHistoryMessage request) {

		ConsumerService.receiveCycleInfo(request);
		return ApiResponse.success(request.mdn());
	}

	@GetMapping(value = "/mdns")
	public List<MdnBizResponse> getMdnAndBizId() {

		return ConsumerService.getMdnAndBizId();
	}
}
