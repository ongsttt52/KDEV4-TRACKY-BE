package kernel360.trackyemulator.application.service.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackyemulator.application.service.dto.request.CycleGpsRequest;
import kernel360.trackyemulator.application.service.dto.request.CycleInfoRequest;
import kernel360.trackyemulator.application.service.dto.response.ApiResponse;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CycleRequestClient {

	private final RestTemplate restTemplate;

	public ApiResponse sendCycleData(EmulatorInstance instance) {

		// CycleInfoRequest DTO 생성
		List<CycleGpsRequest> buffer = new ArrayList<>(instance.getCycleBuffer());

		EmulatorInfo emulatorInfo = instance.getEmulatorInfo();

		CycleInfoRequest request = CycleInfoRequest.of(
			emulatorInfo,
			LocalDateTime.now(),
			buffer
		);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CycleInfoRequest> entity = new HttpEntity<>(request, headers);

		// Cycle 데이터 전송 API 호출
		ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
			"http://hub-service.hub1:8082/hub/car/cycle",
			entity,
			ApiResponse.class
		);

		// API 응답 처리
		ApiResponse apiResponse = response.getBody();
		if (apiResponse == null || !("000".equals(apiResponse.getRstCd()))) {
			throw new IllegalStateException(
				"주기 데이터 전송 실패 " + request.getEmulatorInfo().getMdn());
		}

		log.info("{} → 60초 주기 데이터 전송 완료", request.getEmulatorInfo().getMdn());

		return apiResponse;
	}
}
