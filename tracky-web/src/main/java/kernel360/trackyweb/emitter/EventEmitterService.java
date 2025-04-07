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

	public SseEmitter subscribe(String clientId) {
		SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
		emitters.put(clientId, emitter);

		log.info("SSE 구독 연결: clientId = {}", clientId);

		emitter.onCompletion(() -> emitters.remove(clientId));
		emitter.onTimeout(() -> emitters.remove(clientId));
		emitter.onError((e) -> emitters.remove(clientId));

		try {
			emitter.send(SseEmitter.event()
				.name("init")
				.data("SSE 연결 성공"));
		} catch (Exception e) {
			log.error("초기 SSE 메시지 전송 실패: clientId = {}", clientId, e);
			emitter.completeWithError(e);
			emitters.remove(clientId);
		}

		return emitter;
	}

	public void sendEvent(String eventName, Object data) {
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
	}

	// 🔁 keep-alive 메시지를 15초마다 전송
	@Scheduled(fixedRate = 15000)
	public void sendKeepAlive() {
		emitters.forEach((clientId, emitter) -> {
			try {
				emitter.send(SseEmitter.event().comment("keep-alive"));
			} catch (IOException e) {
				log.warn("SSE keep-alive 실패: clientId = {}", clientId);
				emitter.completeWithError(e);
				emitters.remove(clientId);
			}
		});
	}
}
