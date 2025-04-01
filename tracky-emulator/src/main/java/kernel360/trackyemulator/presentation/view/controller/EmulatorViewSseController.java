package kernel360.trackyemulator.presentation.view.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kernel360.trackyemulator.presentation.view.service.SseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/emulator")
@RequiredArgsConstructor
public class EmulatorViewSseController {

	private final SseService sseService;

	@GetMapping("/stream")
	public SseEmitter stream() {
		return sseService.createEmitter();
	}

}
