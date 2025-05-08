package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import java.time.LocalDate;
import java.util.Optional;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackycore.core.infrastructure.repository.MonthlyStatisticRepository;

public interface MonthlyStatisticDomainRepository
	extends MonthlyStatisticRepository, MonthlyStatisticRepositoryCustom {

	Optional<MonthlyStatisticEntity> findByBizIdAndDate(Long bizId, LocalDate date);
}
