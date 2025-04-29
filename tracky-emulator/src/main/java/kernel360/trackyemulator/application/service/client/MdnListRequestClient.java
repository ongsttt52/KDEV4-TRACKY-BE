package kernel360.trackyemulator.application.service.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel360.trackyemulator.application.service.dto.response.MdnBizResponse;
import kernel360.trackyemulator.infrastructure.exception.EmulatorException;
import kernel360.trackyemulator.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@ToString
public class MdnListRequestClient {

	private final RestTemplate restTemplate;

	@Value("${url.hub-service}")
	private String apiUrl;

	public List<MdnBizResponse> getMdnList() {
		String url = apiUrl + "/mdns";

		MdnBizResponse[] responseArray = restTemplate.getForObject(url, MdnBizResponse[].class);

		if (responseArray == null || responseArray.length == 0) {
			throw EmulatorException.sendError(ErrorCode.MDN_LIST_REQUEST_FAILED);
		}

		List<MdnBizResponse> responses = Arrays.asList(responseArray);
		log.info("서버에서 받아온 mdn list : {}", responses);
		return responses;
	}
}
