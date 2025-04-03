package kernel360.trackyemulator.presentation.view.controller;

import jakarta.servlet.http.HttpSession;
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
	public String showStartForm(Model model, HttpSession session) {
		model.addAttribute("tokenResults", session.getAttribute("tokenResults"));
		model.addAttribute("message", session.getAttribute("message"));
		model.addAttribute("availableCount", session.getAttribute("availableCount"));
		model.addAttribute("engineStatus", session.getAttribute("engineStatus"));
		model.addAttribute("instanceCount", session.getAttribute("instanceCount"));
		return "emulator-start-form";
	}

	@PostMapping("/available")
	public String checkAvailable(HttpSession session) {
		int availableCount = carInstanceManager.getAvailableEmulatorCount();
		session.setAttribute("availableCount", availableCount);
		session.setAttribute("message", "현재 생성 가능한 에뮬레이터 개수: " + availableCount + "대");
		return "redirect:/emulator";
	}

	@PostMapping("/configure")
	public String configure(@RequestParam(name = "count") int count, HttpSession session) {
		int createdInstance = carInstanceManager.createEmulator(count);
		session.setAttribute("instanceCount", createdInstance);
		session.setAttribute("message", createdInstance + "대의 에뮬레이터 생성 완료!");
		return "redirect:/emulator";
	}

	@PostMapping("/fetch-token")
	public String fetchToken(HttpSession session) {
		Map<String, String> result = carInstanceManager.fetchAllTokens();
		session.setAttribute("tokenResults", result);
		session.setAttribute("message", "토큰 및 초기 정보 받아오기 완료!");
		return "redirect:/emulator";
	}

	@PostMapping("/start")
	public String startEmulator(HttpSession session) {
		carInstanceManager.sendStartRequests();
		session.setAttribute("engineStatus", "ON");
		session.setAttribute("message", "🚗 모든 차량 시동 ON 요청 완료!");
		return "redirect:/emulator";
	}

	@PostMapping("/stop")
	public String stopEmulator(HttpSession session) {
		carInstanceManager.sendStopRequests();
		session.setAttribute("engineStatus", "OFF");
		session.setAttribute("message", "🛑 모든 차량 시동 OFF 요청 완료!");
		return "redirect:/emulator";
	}

	@PostMapping("/reset")
	public String resetSession(HttpSession session) {
		session.invalidate();
		return "redirect:/emulator";
	}
}