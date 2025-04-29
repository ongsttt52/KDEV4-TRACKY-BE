package kernel360.trackyemulator.presentation.view.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kernel360.trackyemulator.application.service.CarInstanceManager;
import kernel360.trackyemulator.application.service.dto.response.MdnBizResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/emulator")
@RequiredArgsConstructor
@Slf4j
public class EmulatorViewController {

	private final CarInstanceManager carInstanceManager;

	@GetMapping
	public String showStartForm(Model model, HttpSession session) {
		model.addAttribute("tokenResults", session.getAttribute("tokenResults"));
		model.addAttribute("message", session.getAttribute("message"));
		model.addAttribute("availableMdnAndBizId", session.getAttribute("availableMdnAndBizId"));
		model.addAttribute("engineStatus", session.getAttribute("engineStatus"));
		model.addAttribute("instanceCount", session.getAttribute("instanceCount"));
		return "emulator-start-form";
	}

	@PostMapping("/available")
	public String checkAvailable(HttpSession session) {
		List<MdnBizResponse> availableMdnAndBizId = carInstanceManager.getAvailableMdnAndBizId();
		session.setAttribute("availableMdnAndBizId", availableMdnAndBizId);
		session.setAttribute("message", "현재 생성 가능한 에뮬레이터 개수: " + availableMdnAndBizId.size() + "대");
		return "redirect:/emulator";
	}

	@PostMapping("/configure-selected")
	public String configure(@RequestParam("selectedMdns") List<String> mdnList, HttpSession session) {
		int createdInstance = carInstanceManager.createEmulator(mdnList);
		session.setAttribute("instanceCount", createdInstance);
		session.setAttribute("message", createdInstance + "대의 에뮬레이터 생성 완료!");

		// 리스트를 더 이상 보여주지 않기 위해 제거
		session.removeAttribute("availableMdnAndBizId");
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
		carInstanceManager.resetEmulator();
		return "redirect:/emulator";
	}
}
