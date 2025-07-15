package kernel360.trackyconsumer.consumer.application.service;

import java.util.ArrayList;
import java.util.List;
import kernel360.trackyconsumer.consumer.application.dto.request.CarOnOffRequest;
import kernel360.trackyconsumer.consumer.application.dto.request.GpsHistoryMessage;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageListener {
	private final ConsumerService consumerService;

	@RabbitListener(queues = "on-off-queue")
	public void receiveCarOnOffMessage(@Payload CarOnOffRequest message,
		@Header("amqp_receivedRoutingKey") String routingKey) {
		try {
			switch (routingKey) {
				case "onKey":
					consumerService.processOnMessage(message);
					break;
				case "offKey":
					consumerService.processOffMessage(message);
					break;
			}
		} catch (Exception e) {
			log.error("Error processing message: {}", e.getMessage());
		}
	}

	// GPS 정보 처리 큐
	@RabbitListener(queues = "gps-queue", containerFactory = "batchRabbitListenerContainerFactory")
	public void receiveCarMessages_bulk(List<GpsHistoryMessage> messages) {
		log.info("GPS 메시지 {}건 수신 - Thread: {}", messages.size(), Thread.currentThread().getName());

		List<GpsHistoryEntity> gpses = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (GpsHistoryMessage message : messages) {
			try {
				gpses.addAll(consumerService.receiveCycleInfo_bulk(message));
			} catch (Exception e) {
				log.error("GPS 메시지 처리 중 오류 발생: {}", e.getMessage());
			}
		}

		long end = System.currentTimeMillis();

		log.info("receiveCarMessages_bulk - receiveCycleInfo_bulk : {}ms 소요", end - start);

		start = System.currentTimeMillis();
		consumerService.saveAllGps(gpses);
		end = System.currentTimeMillis();

		log.info("receiveCarMessages_bulk - saveAllGps : {}ms 소요", end - start);
	}

	// @RabbitListener(queues = "gps-queue", containerFactory = "batchRabbitListenerContainerFactory")
	// public void receiveCarMessages(GpsHistoryMessage message) {
	// 	log.info("GPS 메시지 수신");
	//
	// 	long start = System.currentTimeMillis();
	// 	try {
	// 		consumerService.receiveCycleInfo_bulk(message);
	// 	} catch (Exception e) {
	// 		log.error("GPS 메시지 처리 중 오류 발생: {}", e.getMessage());
	// 	}
	// 	long end = System.currentTimeMillis();
	//
	// 	log.info("GPS 메시지 처리 완료 | {}ms 소요", end - start);
	// }
}
