package kernel360.trackyemulator.presentation.view.controller;

import kernel360.trackyemulator.application.service.CarInstanceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/emulator")
@RequiredArgsConstructor
public class EmulatorViewController {

	private final CarInstanceManager carInstanceManager;

	@GetMapping
	public String showStartForm(Model model) {
		model.addAttribute("tokenResults", null);
		model.addAttribute("message", null);
		return "emulator-start-form";
	}

	@PostMapping("/configure")
	public String configure(@RequestParam(name = "count") int count, Model model) {
		int createdInstance = carInstanceManager.createEmulator(count);
		model.addAttribute("message", createdInstance + "대의 에뮬레이터 개수 설정 완료!");
		return "emulator-start-form";
	}

	@PostMapping("/fetch-token")
	public String fetchToken(Model model) {
		Map<String, String> result = carInstanceManager.fetchAllTokens();
		model.addAttribute("tokenResults", result);
		model.addAttribute("message", "토큰 및 초기 정보 받아오기 완료!");
		return "emulator-start-form";
	}

	@PostMapping("/start")
	public String startEmulator(Model model) {
		carInstanceManager.sendStartRequests();
		model.addAttribute("message", "모든 차량 시동 ON 요청 완료!");
		return "emulator-start-form";
	}

	@PostMapping("/stop")
	public String stopEmulator(Model model) {
		carInstanceManager.sendStopRequests();
		model.addAttribute("message", "모든 차량 시동 OFF 요청 완료!");
		return "emulator-start-form";
	}
}