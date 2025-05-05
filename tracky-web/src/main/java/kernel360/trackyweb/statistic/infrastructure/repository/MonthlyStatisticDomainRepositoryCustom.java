package kernel360.trackyweb.statistic.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackyweb.statistic.domain.dto.MonthlyStat;

public interface MonthlyStatisticDomainRepositoryCustom {

	List<MonthlyStat> getMonthlyStats(Long bizId, LocalDate currentDate, LocalDate targetDate);
}
