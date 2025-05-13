package kernel360.trackyweb.realtime.application.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.emitter.EventEmitterService;
import kernel360.trackyweb.emitter.GpsPointResponse;
import kernel360.trackyweb.realtime.application.dto.request.GpsHistoryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {
	private final DriveDomainProvider driveDomainProvider;
	private final EventEmitterService eventEmitterService;

	// GPS 정보 처리 큐
	@RabbitListener(queues = "web-queue")
	public void receiveCarMessages(GpsHistoryMessage messages) {
		log.info("Web-Queue 메시지 수신: {}", messages);

		try {
			Long driveId = driveDomainProvider.findActiveDriveIdByMdn(messages.mdn());

			List<GpsPointResponse> gpsList = messages.cList().stream()
				.map(GpsPointResponse::from)
				.toList();

			eventEmitterService.sendToDriveId(driveId.toString(), "drive_path", gpsList);

			log.info("SSE 전송 시도: driveId={}, gpsCount={}", driveId, gpsList.size());
		} catch (Exception e) {
			log.error("GPS 메시지 처리 실패: {}", messages, e);
		}
	}
}
