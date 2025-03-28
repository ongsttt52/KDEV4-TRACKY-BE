package kernel360.trackyemulator.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kernel360.trackyemulator.application.service.CarInstanceManager;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/emulator")
@RequiredArgsConstructor
public class EmulatorViewController {

	private final CarInstanceManager carInstanceManager;

	@GetMapping("/")
	public String showStartForm() {
		return "emulator-start-form";
	}

	@PostMapping("/configure")
	public String configure(@RequestParam(name = "count") int count, Model model) {
		System.out.println("입력된 에뮬레이터 개수: " + count);
		carInstanceManager.configureCount(count);
		model.addAttribute("message", count + "대의 에뮬레이터 개수 설정 완료!");
		return "emulator-start-form";
	}

	@PostMapping("/fetch-token")
	public String fetchToken(Model model) {
		// carInstanceManager.fetchAllTokens();
		model.addAttribute("message", "토큰 및 초기 정보 모두 받아오기 완료!");
		return "emulator-start-form";
	}

	@PostMapping("/start")
	public String startEmulator(Model model) {
		// carInstanceManager.sendStartRequests();
		model.addAttribute("message", "모든 차량 시동 ON 요청 완료!");
		return "emulator-status";
	}


}
