package kernel360.trackyemulator.application.service.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

		String url = "http://hub-service.hub1:8082/hub/car/mdns"; // 실제 엔드포인트로 수정

		String[] mdnArray = restTemplate.getForObject(url, String[].class);

		log.info("서버에서 받아온 mdn list : {}", mdnArray.toString());

		return Arrays.asList(mdnArray);
	}
}
