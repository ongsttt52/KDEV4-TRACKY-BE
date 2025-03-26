package kernel360.trackyweb.biz.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.infrastructure.entity.BizEntity;
import kernel360.trackyweb.biz.application.BizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/biz")
@RequiredArgsConstructor
@Tag(name = "Biz API", description = "비즈니스 관련 API입니다.")
public class BizController {

	private final BizService bizService;

	@GetMapping("/getall")
	@Operation(summary = "전체 비즈 조회", description = "DB에 있는 모든 비즈 리스트를 반환합니다.")
	public List<BizEntity> getAll(
	) {
		var result = bizService.getAll();
		log.info("result: {}", result);
		return result;
	}
}
