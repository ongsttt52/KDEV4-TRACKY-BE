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
	private static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	public static Map<String, SseEmitter> getTest() {
		return emitters;
	}

	public SseEmitter subscribe(String driveId) {

		SseEmitter oldEmitter = emitters.get(driveId);
		if (oldEmitter != null) {
			oldEmitter.complete(); // 연결 종료
			emitters.remove(driveId);
			log.info("기존 SSE 연결 종료: driveId = {}", driveId);
		}

		SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

		emitters.put(driveId, emitter);

		log.info("SSE 연결: driveId = {}, emitter={}", driveId, emitter);

		emitter.onCompletion(() -> {
			log.info("SSE 연결 종료: driveId = {}", driveId);
			emitters.remove(driveId);
		});

		emitter.onTimeout(() -> emitters.remove(driveId));
		emitter.onError((e) -> emitters.remove(driveId));

		try {
			emitter.send(SseEmitter.event().comment("connected"));

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
		log.info("emitters.get({}) 결과: {}", driveId, emitter);

		if (emitter == null) {
			log.warn("SSE 전송 실패: driveId={}, event={}", driveId, eventName);
			return;
		}

		try {
			emitter.send(SseEmitter.event().name(eventName).data(data));
			log.info("emitter.send 성공");
		} catch (Exception e) {
			log.error("emitter.send 실패: {}", e.getMessage(), e);
			emitter.completeWithError(e);
			emitters.remove(driveId);
		}

		/*try {
			String json = new ObjectMapper().writeValueAsString(data);
			log.info("SSE 전송 시도: driveId={}, event={}, data={}", driveId, eventName, json);
			emitter.send(SseEmitter.event().name(eventName).data(json));
			log.debug("SSE 전송 완료: driveId={}, event={}", driveId, eventName);
		} catch (Exception e) {
			log.warn("SSE 전송 실패: driveId={}, eventName={}", driveId, e.getMessage());
			emitter.completeWithError(e);
			emitters.remove(driveId);
		}*/
	}

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
