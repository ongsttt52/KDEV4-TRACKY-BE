package kernel360.trackyconsumer.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyconsumer.application.service.CarTestService;
import kernel360.trackyconsumer.presentation.dto.ApiResponse;
import kernel360.trackyconsumer.presentation.dto.CycleInfoRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CarTestController {

	private final CarTestService CarTestService;

	@PostMapping(value = "/test")
	public ApiResponse sendCycleInfo(@RequestBody CycleInfoRequest cycleInfoRequest) {
		
		CarTestService.sendCycleInfo(cycleInfoRequest);
		return new ApiResponse("000", "Success", cycleInfoRequest.getMdn());
	}
}
