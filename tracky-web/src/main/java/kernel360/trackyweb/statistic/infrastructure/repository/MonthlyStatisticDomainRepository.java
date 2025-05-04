package kernel360.trackyweb.statistic.infrastructure.repository;

import java.time.LocalDate;
import java.util.Optional;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackycore.core.infrastructure.repository.MonthlyStatisticRepository;

public interface MonthlyStatisticDomainRepository extends MonthlyStatisticRepository {

	Optional<MonthlyStatisticEntity> findByBizIdAndDate(Long bizId, LocalDate date);
}
