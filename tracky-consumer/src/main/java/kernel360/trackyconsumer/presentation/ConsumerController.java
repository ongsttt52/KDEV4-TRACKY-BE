package kernel360.trackyconsumer.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyconsumer.application.dto.CarOnOffRequest;
import kernel360.trackyconsumer.application.dto.GpsHistoryMessage;
import kernel360.trackyconsumer.application.service.ConsumerService;
import kernel360.trackyconsumer.presentation.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/consume")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

	private final ConsumerService ConsumerService;

	@PostMapping("/on")
	public ApiResponse postCarOn(@RequestBody CarOnOffRequest request) {

		ConsumerService.processOnMessage(request);
		return new ApiResponse("000", "Success", request.getMdn());
	}

	@PostMapping("/off")
	public ApiResponse postCarOff(@RequestBody CarOnOffRequest request) {

		ConsumerService.processOffMessage(request);
		return new ApiResponse("000", "Success", request.getMdn());
	}

	@PostMapping("/cycle")
	public ApiResponse postCarCycle(@RequestBody GpsHistoryMessage request) {

		ConsumerService.receiveCycleInfo(request);
		return new ApiResponse("000", "Success", request.getMdn());
	}
}
