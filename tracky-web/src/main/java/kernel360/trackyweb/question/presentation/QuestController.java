package kernel360.trackyweb.question.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.question.application.QuestService;
import kernel360.trackyweb.question.domain.QuestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/quests")
@RestController
@RequiredArgsConstructor
public class QuestController {

	private final QuestService questService;

	@PostMapping("/create")
	public ApiResponse<QuestEntity> sendQuest(
		@RequestBody QuestEntity questEntity
	) {
		log.info("questEntity: {}", questEntity);
		return ApiResponse.success(questService.sendQuest(questEntity));
	}

}
