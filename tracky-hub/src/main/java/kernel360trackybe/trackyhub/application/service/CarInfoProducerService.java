package kernel360trackybe.trackyhub.application.service;

import kernel360trackybe.trackyhub.config.RabbitMQConfig;
import kernel360trackybe.trackyhub.application.dto.CycleInfoRequest;
import kernel360trackybe.trackyhub.application.dto.CycleGpsRequest;
import kernel360trackybe.trackyhub.application.dto.GpsHistoryMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarInfoProducerService {

	private final RabbitTemplate rabbitTemplate;

	/**
	 * GPS 정보 RabbitMQ로 전송 및 누적주행 거리 계산해서 RabbitMQ로 전송
	 */
	public void sendCarInfo(CycleInfoRequest carInfo) {

		// 요청별로 라우팅 키를 설정하여 순서 보장
		String routingKey = RabbitMQConfig.generateRoutingKey(carInfo.getMdn());

		// GPS 정보 메시지 전송
		List<CycleGpsRequest> cycleGpsRequestList = carInfo.getCList();
		for (int i = 0; i < carInfo.getCCnt(); i++) {

			sendGPS(carInfo, cycleGpsRequestList.get(i), routingKey);
		}
	}

	public void sendGPS(CycleInfoRequest carInfo, CycleGpsRequest cycleGpsRequest, String routingKey) {

		GpsHistoryMessage gpsHistoryMessage = GpsHistoryMessage.from(carInfo, cycleGpsRequest);

		rabbitTemplate.convertAndSend(
			RabbitMQConfig.EXCHANGE_NAME,
			routingKey,
			gpsHistoryMessage
		);
	}
}