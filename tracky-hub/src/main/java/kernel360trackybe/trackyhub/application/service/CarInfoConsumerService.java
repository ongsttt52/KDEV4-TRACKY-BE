package kernel360trackybe.trackyhub.application.service;

import kernel360trackybe.trackyhub.config.RabbitMQConfig;
import kernel360trackybe.trackyhub.domain.History;
import kernel360trackybe.trackyhub.application.dto.GpsHistoryMessage;
import kernel360trackybe.trackyhub.infrastructure.repository.CarRepository;
import kernel360trackybe.trackyhub.infrastructure.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarInfoConsumerService {

	private final HistoryRepository historyRepository;
	private final CarRepository carRepository;

	// GPS 정보 처리 큐
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void receiveCarMessage(GpsHistoryMessage message) {

		History history = History.from(message);

		historyRepository.save(history);
	}
}