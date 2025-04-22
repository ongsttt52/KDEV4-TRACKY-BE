package kernel360.trackyemulator.application.service.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackyemulator.application.service.dto.request.TokenRequest;
import kernel360.trackyemulator.application.service.dto.response.ApiResponse;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.exception.EmulatorException;
import kernel360.trackyemulator.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenRequestClient {

	private final RestTemplate restTemplate;

	public String getToken(EmulatorInstance instance) {

		//TokenRequest DTO 생성
		TokenRequest request = TokenRequest.toRequest(instance.getMdn(), instance.getEmulatorInfo());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TokenRequest> entity = new HttpEntity<>(request, headers);

		String url = "http://hub-service.hub1:8082/hub/car/token";

		ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, entity, ApiResponse.class);
		ApiResponse apiResponse = response.getBody();

		if (apiResponse == null || !"000".equals(apiResponse.rstCd()) || apiResponse.token() == null) {
			log.error("토큰 요청 실패: 응답 코드={}, 응답 메시지={}",
				apiResponse != null ? apiResponse.rstCd() : "null",
				apiResponse != null ? apiResponse.rstMsg() : "응답 없음");

			throw EmulatorException.sendError(ErrorCode.TOKEN_REQUEST_FAILED);
		}
		return apiResponse.token();

	}
}
