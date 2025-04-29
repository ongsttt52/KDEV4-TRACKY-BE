package kernel360.trackyweb.realtime.application.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import kernel360.trackyweb.common.sse.GlobalSseEvent;
import kernel360.trackyweb.emitter.EventEmitterService;
import kernel360.trackyweb.realtime.application.dto.request.GpsHistoryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageListener {
	private final GlobalSseEvent globalSseEvent;
	private final EventEmitterService eventEmitterService;

	// GPS 정보 처리 큐
	@RabbitListener(queues = "web-queue")
	public void receiveCarMessages(GpsHistoryMessage messages) {

		eventEmitterService.sendEvent("gps_data", messages);
		log.info("Web-Queue 메시지 SSE 전송: {}", messages.toString());
	}
}
