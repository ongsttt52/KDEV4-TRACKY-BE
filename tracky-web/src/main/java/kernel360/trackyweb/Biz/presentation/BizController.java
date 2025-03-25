package kernel360.trackyweb.Biz.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyweb.Biz.application.BizService;
import kernel360.trackyweb.Biz.infrastructure.entity.BizEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/biz")
@RequiredArgsConstructor
public class BizController {

	private final BizService bizService;

	@GetMapping("/getall")
	public List<BizEntity> getAll(
	) {
		var result = bizService.getAll();
		log.info("result: {}", result);
		return result;
	}
}
