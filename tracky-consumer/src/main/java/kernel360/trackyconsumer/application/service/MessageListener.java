package kernel360.trackyconsumer.application.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import kernel360.trackyconsumer.application.dto.CarOnOffRequest;
import kernel360.trackyconsumer.application.dto.GpsHistoryMessage;
import kernel360.trackyconsumer.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageListener {
	private final ConsumerService consumerService;

	@RabbitListener(queues = RabbitMQConfig.ONOFF_QUEUE_NAME)
	public void receiveCarOnOffMessage(@Payload CarOnOffRequest message,
		@Header("amqp_receivedRoutingKey") String routingKey) {
		log.info("메시지 수신: {}", message.toString());
		try {
			switch (routingKey) {
				case "on":
					consumerService.processOnMessage(message);
					break;
				case "off":
					consumerService.processOffMessage(message);
					break;
			}
		} catch (Exception e) {
			log.error("Error processing message: {}", e.getMessage());
		}
	}

	// GPS 정보 처리 큐
	@RabbitListener(queues = RabbitMQConfig.GPS_QUEUE_NAME, containerFactory = "batchRabbitListenerContainerFactory")
	public void receiveCarMessages(List<GpsHistoryMessage> messages) {
		log.info("GPS 메시지 배치 수신: {} 개", messages.size());

		for (GpsHistoryMessage message : messages)
			consumerService.receiveCycleInfo(message);
	}

	// // DLQ 메시지 처리 리스너
	// @RabbitListener(queues = "dead-letter-queue")
	// public void processDLQ(GpsHistoryMessage failedMessage) {
	// 	log.warn("DLQ 메시지 발견: {}", failedMessage);
	// 	// 실패 원인 분석 및 알림 전송
	// 	// 필요시 수정 후 원래 큐로 재전송
	// }
}
