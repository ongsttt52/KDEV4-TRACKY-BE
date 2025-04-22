package kernel360.trackyemulator.application.service.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

	public List<String> getMdnList() {
		String url = "http://hub-service.hub1:8082/hub/car/mdns";

		String[] mdnArray = restTemplate.getForObject(url, String[].class);

		if (mdnArray == null || mdnArray.length == 0) {
			throw EmulatorException.sendError(ErrorCode.MDN_LIST_REQUEST_FAILED);
		}

		log.info("서버에서 받아온 mdn list : {}", Arrays.toString(mdnArray));
		return Arrays.asList(mdnArray);
	}
}
