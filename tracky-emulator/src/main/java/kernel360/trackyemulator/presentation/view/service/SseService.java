package kernel360.trackyemulator.presentation.view.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kernel360.trackyemulator.presentation.view.dto.CycleLogResponse;

@Service
public class SseService {

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	public SseEmitter createEmitter() {
		SseEmitter emitter = new SseEmitter(0L); // 무제한 타임아웃
		emitters.add(emitter);

		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));

		return emitter;
	}

	public void sendMessage(CycleLogResponse response) {
		for (SseEmitter emitter : emitters) {
			try {
				emitter.send(SseEmitter.event()
					.name("cycleLog")
					.data(response));
			} catch (Exception e) {
				emitters.remove(emitter);
			}
		}
	}
}