package kernel360.trackyweb.realtime.application.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import kernel360.trackyweb.realtime.application.dto.request.GpsHistoryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

	// GPS 정보 처리 큐
	@RabbitListener(queues = "web-queue", containerFactory = "batchRabbitListenerContainerFactory")
	public void receiveCarMessages(List<GpsHistoryMessage> messages) {
		log.info("GPS 메시지 배치 수신: {} 개", messages.size());

		messages.forEach(message -> {
			log.info("GPS 메시지 수신: {}", message.toString());
		});
	}
}
