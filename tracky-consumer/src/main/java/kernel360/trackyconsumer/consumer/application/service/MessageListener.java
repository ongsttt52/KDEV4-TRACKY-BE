package kernel360.trackyconsumer.consumer.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
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
	// --- 1500개 메시지 측정용 변수 추가 ---
	private static final AtomicInteger totalProcessedMessages = new AtomicInteger(0);
	private static final AtomicLong startTimeFor1500Messages = new AtomicLong(0);
	private static final int TARGET_MESSAGE_COUNT = 3000;
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
		// --- 1500개 메시지 측정 시작 로직 ---
		// 첫 메시지 묶음이 들어올 때 시간 기록 (단 한 번만)
		if (totalProcessedMessages.get() == 0 && startTimeFor1500Messages.get() == 0) {
			startTimeFor1500Messages.set(System.currentTimeMillis());
			log.info("--- 1500개 메시지 처리 시간 측정 시작 ---");
		}
		// ------------------------------------

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

		// --- 1500개 메시지 측정 종료 로직 ---
		// 현재 처리된 메시지 개수를 더하고
		int currentTotal = totalProcessedMessages.addAndGet(messages.size());

		// 만약 TARGET_MESSAGE_COUNT를 넘었거나 정확히 도달했다면 시간 출력
		if (currentTotal >= TARGET_MESSAGE_COUNT) {
			long totalElapsedTime = System.currentTimeMillis() - startTimeFor1500Messages.get();
			log.info("--- 총 {}개 메시지 처리 완료. 총 소요 시간: {}ms ---", TARGET_MESSAGE_COUNT, totalElapsedTime);

			// 측정 완료 후 변수 초기화 (필요하다면 다음 측정을 위해)
			totalProcessedMessages.set(0);
			startTimeFor1500Messages.set(0);
		}
		// ------------------------------------
	}
}
