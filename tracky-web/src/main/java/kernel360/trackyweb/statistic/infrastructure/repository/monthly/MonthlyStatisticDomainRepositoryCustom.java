package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;

public interface MonthlyStatisticDomainRepositoryCustom {

	List<MonthlyStatisticResponse.MonthlyStats> getMonthlyStats(Long bizId, LocalDate currentDate,
		LocalDate targetDate);
}
