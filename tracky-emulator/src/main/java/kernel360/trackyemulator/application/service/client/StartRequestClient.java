package kernel360.trackyemulator.application.service.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackyemulator.application.service.dto.request.CarOnOffRequest;
import kernel360.trackyemulator.application.service.dto.response.ApiResponse;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.exception.EmulatorException;
import kernel360.trackyemulator.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartRequestClient {

	private final RestTemplate restTemplate;

	public ApiResponse sendCarStart(EmulatorInstance car) {

		String url = "http://hub-service.hub1:8082/hub/car/on";

		//CarOnOffRequest DTO 생성
		CarOnOffRequest request = CarOnOffRequest.ofOn(car);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CarOnOffRequest> entity = new HttpEntity<>(request, headers);

		ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, entity, ApiResponse.class);
		ApiResponse apiResponse = response.getBody();

		if (apiResponse == null || !"000".equals(apiResponse.rstCd())) {
			log.error("시동 ON 요청 실패 - mdn: {}, 응답: {}", request.mdn(),
				apiResponse != null ? apiResponse.rstMsg() : "null");
			throw EmulatorException.sendError(ErrorCode.START_REQUEST_FAILED);
		}

		return apiResponse;
	}
}
