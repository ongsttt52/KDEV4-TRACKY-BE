package kernel360.trackyemulator.application.service.client;

import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackyemulator.application.service.dto.request.CycleInfoRequest;
import kernel360.trackyemulator.application.service.dto.response.ApiResponse;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.exception.EmulatorException;
import kernel360.trackyemulator.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CycleRequestClient {

	private final RestTemplate restTemplate;

	public ApiResponse sendCycleData(EmulatorInstance instance) {

		String url = "http://hub-service.hub1:8082/hub/car/cycle";

		// CycleInfoRequest DTO 생성
		EmulatorInfo emulatorInfo = instance.getEmulatorInfo();
		CycleInfoRequest request = CycleInfoRequest.of(instance.getMdn(), emulatorInfo, LocalDateTime.now(),
			instance.getCycleBuffer());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CycleInfoRequest> entity = new HttpEntity<>(request, headers);

		ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, entity, ApiResponse.class);
		ApiResponse apiResponse = response.getBody();

		if (apiResponse == null || !"000".equals(apiResponse.rstCd())) {
			log.error("주기정보 전송 실패 - mdn: {}, 응답: {}", request.mdn(),
				apiResponse != null ? apiResponse.rstMsg() : "null");
			throw EmulatorException.sendError(ErrorCode.CYCLE_SEND_FAILED);
		}

		log.info("주기정보 전송 성공 - mdn: {}", request.mdn());
		return apiResponse;

	}
}
