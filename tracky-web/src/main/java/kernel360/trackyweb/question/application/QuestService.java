package kernel360.trackyweb.question.application;

import org.springframework.stereotype.Service;

import kernel360.trackyweb.question.domain.QuestEntity;
import kernel360.trackyweb.question.infrastructure.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestService {

	private final QuestRepository questRepository;

	public QuestEntity sendQuest(QuestEntity questEntity) {
		log.info("sendQuest: {}", questEntity);

		return questRepository.save(questEntity);
	}

}
