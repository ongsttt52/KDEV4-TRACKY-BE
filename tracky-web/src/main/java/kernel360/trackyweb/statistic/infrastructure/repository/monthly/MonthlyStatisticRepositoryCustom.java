package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminGraphStatsResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;

public interface MonthlyStatisticRepositoryCustom {

	MonthlyStatisticEntity findLatestMonthlyStatistic(String bizUuid);

	MonthlyStatisticEntity findByBizIdAndDate(Long bizId, LocalDate date);

	List<MonthlyStatisticResponse.MonthlyStats> getMonthlyStats(Long bizId, LocalDate currentDate,
		LocalDate targetDate);

	List<AdminGraphStatsResponse.NonOperatedCar> getNonOperatedCarWithBizName();
}
