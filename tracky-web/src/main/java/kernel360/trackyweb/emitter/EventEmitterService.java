package kernel360.trackyweb.emitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventEmitterService {
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter subscribe(String driveId) {
		SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
		emitters.put(driveId, emitter);

		log.info("SSE 연결: driveId = {}", driveId);

		emitter.onCompletion(() -> emitters.remove(driveId));
		emitter.onTimeout(() -> emitters.remove(driveId));
		emitter.onError((e) -> emitters.remove(driveId));

		try {
			emitter.send(SseEmitter.event()
				.name("init")
				.data("SSE 연결 성공"));
		} catch (Exception e) {
			log.error("초기 SSE 메시지 전송 실패: driveId = {}", driveId, e);
			emitter.completeWithError(e);
			emitters.remove(driveId);
		}

		return emitter;
	}

	public void sendToDriveId(String driveId, String eventName, Object data) {
		SseEmitter emitter = emitters.get(driveId);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name(eventName).data(data));
				log.debug("SSE 전송 완료: driveId={}, event={}", driveId, eventName);
			} catch (IOException e) {
				log.warn("SSE 전송 실패: driveId={}, eventName={}", driveId, e.getMessage());
				emitter.completeWithError(e);
				emitters.remove(driveId);
			}
		}
	}

/*	public boolean hasDrive(String driveId) {
		return emitters.containsKey(driveId);
	}*/

/*	public void sendEvent(String eventName, Object data) {
		emitters.forEach((key, emitter) -> {
			try {
				emitter.send(SseEmitter.event()
					.name(eventName)
					.data(data));
			} catch (IOException e) {
				log.warn("SSE 연결 실패 (eventName: {}): {}", eventName, e.getMessage());
				emitter.completeWithError(e);
				emitters.remove(key);
			}
		});
	}*/

	// keep-alive 메시지를 15초마다 전송
	@Scheduled(fixedRate = 15000)
	public void sendKeepAlive() {
		emitters.forEach((driveId, emitter) -> {
			try {
				emitter.send(SseEmitter.event().comment("keep-alive"));
			} catch (IOException e) {
				log.warn("SSE keep-alive 실패: driveId = {}", driveId);
				emitter.completeWithError(e);
				emitters.remove(driveId);
			}
		});
	}
}
