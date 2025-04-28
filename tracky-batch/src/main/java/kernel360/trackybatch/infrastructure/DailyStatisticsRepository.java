package kernel360.trackybatch.infrastructure;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;

public interface DailyStatisticsRepository extends JpaRepository<DailyStatisticEntity, Long> {
	Optional<DailyStatisticEntity> findByBizUuidAndDate(String bizUuid, LocalDateTime date);

}
