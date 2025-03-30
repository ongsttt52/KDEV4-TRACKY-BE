package kernel360.trackyemulator.application.service.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackyemulator.presentation.dto.ApiResponse;
import kernel360.trackyemulator.presentation.dto.TokenRequest;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenRequestClient {

	private final RestTemplate restTemplate;

	public String getToken(EmulatorInstance instance) {

		//TokenRequest DTO set
		TokenRequest request = TokenRequest.from(instance);


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<TokenRequest> entity = new HttpEntity<>(request, headers);

		//getToken Request 요청
		ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
			"http://localhost:8082/api/car/토큰 요청 url 필요",
			entity,
			ApiResponse.class
		);

		//응답
		ApiResponse apiResponse = response.getBody();
		if (apiResponse == null || !("000".equals(apiResponse.getRstCd()))) {
			throw new IllegalStateException(
				"토큰 요청 실패 " + instance.getMdn());
		}

		return apiResponse.getToken();
	}
}
