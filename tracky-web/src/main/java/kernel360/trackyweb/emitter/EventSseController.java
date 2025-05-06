package kernel360.trackyweb.emitter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventSseController {

	private final EventEmitterService eventEmitterService;

	@GetMapping("/subscribe")
	public SseEmitter subscribe(@RequestParam String driveId) {
		return eventEmitterService.subscribe(driveId);
	}

}
