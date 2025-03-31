package kernel360.trackyemulator.application.service.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackyemulator.presentation.dto.ApiResponse;
import kernel360.trackyemulator.presentation.dto.CarOnOffRequest;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StopRequestClient {

    private final RestTemplate restTemplate;

    public ApiResponse sendCarStop(EmulatorInstance car) {

        //CarOnOffRequest DTO 생성
        CarOnOffRequest request = CarOnOffRequest.ofOff(car);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CarOnOffRequest> entity = new HttpEntity<>(request, headers);

        //sendCarStop 전송
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/http://localhost:8082/api/car/시동 오프",
                entity,
                ApiResponse.class
        );

        //API 응답
        ApiResponse apiResponse = response.getBody();
        if (apiResponse == null || !("000".equals(apiResponse.getRstCd()))) {
            throw new IllegalStateException(
                    "Stop 정보 전송 실패 " + apiResponse.getMdn());
        }

        return apiResponse;
    }
}
