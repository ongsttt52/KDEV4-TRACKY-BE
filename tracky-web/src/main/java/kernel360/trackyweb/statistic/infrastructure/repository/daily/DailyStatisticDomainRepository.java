package kernel360.trackyweb.statistic.infrastructure.repository.daily;

import java.time.LocalDate;
import java.util.Optional;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackycore.core.infrastructure.repository.DailyStatisticRepository;

public interface DailyStatisticDomainRepository extends DailyStatisticRepository, DailyStatisticRepositoryCustom {

	Optional<DailyStatisticEntity> findByBizIdAndDate(Long bizId, LocalDate date);

}
