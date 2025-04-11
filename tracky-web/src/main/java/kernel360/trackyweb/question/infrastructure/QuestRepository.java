package kernel360.trackyweb.question.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackyweb.question.domain.QuestEntity;

@Repository
public interface QuestRepository extends JpaRepository<QuestEntity, Long> {
}
