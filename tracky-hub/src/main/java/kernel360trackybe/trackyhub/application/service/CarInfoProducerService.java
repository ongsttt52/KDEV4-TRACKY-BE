package kernel360trackybe.trackyhub.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.provider.CarProvider;
import kernel360trackybe.trackyhub.application.dto.request.CarOnOffRequest;
import kernel360trackybe.trackyhub.application.dto.request.CycleInfoRequest;
import kernel360trackybe.trackyhub.application.dto.request.GpsHistoryMessage;
import kernel360trackybe.trackyhub.application.dto.response.MdnBizResponse;
import kernel360trackybe.trackyhub.config.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarInfoProducerService {

	private final RabbitTemplate rabbitTemplate;
	private final RabbitMQProperties rabbitMQProperties;
	private final CarProvider carProvider;

	public void sendCarStart(CarOnOffRequest carOnOffRequest) {

		rabbitTemplate.convertAndSend(
			rabbitMQProperties.getExchange().getCarInfo(),
			rabbitMQProperties.getRouting().getOnKey(),
			carOnOffRequest
		);
	}

	public void sendCarStop(CarOnOffRequest carOnOffRequest) {
		log.info("Car stop requested: {}", carOnOffRequest);

		rabbitTemplate.convertAndSend(
			rabbitMQProperties.getExchange().getCarInfo(),
			rabbitMQProperties.getRouting().getOffKey(),
			carOnOffRequest
		);
	}

	/**
	 * GPS 정보 RabbitMQ로 전송 및 누적주행 거리 계산해서 RabbitMQ로 전송
	 */
	public void sendCycleInfo(CycleInfoRequest carInfo) {

		GpsHistoryMessage gpsHistoryMessage = GpsHistoryMessage.from(carInfo.mdn(),
			carInfo.oTime(),
			carInfo.cCnt(), carInfo.cList());

		log.info("GPS 전송:{}", gpsHistoryMessage.toString());
		rabbitTemplate.convertAndSend(
			rabbitMQProperties.getExchange().getCarInfo(),
			rabbitMQProperties.getRouting().getGpsKey(),
			gpsHistoryMessage
		);
	}

	public String getToken() {
		return UUID.randomUUID().toString();
	}

	@Transactional(readOnly = true)
	public List<MdnBizResponse> getMdnAndBizId() {
		List<CarEntity> cars = carProvider.findAll();
		return cars.stream()
			.map(car -> MdnBizResponse.of(car.getMdn(), carProvider.findByMdn(car.getMdn()).getBiz().getId()))
			.toList();
	}
}
