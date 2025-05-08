package kernel360.trackyweb.statistic.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kernel360.trackyweb.statistic.application.SchedulerService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticScheduler {
	private final SchedulerService schedulerService;

	@Scheduled(cron = "0 0 1 * * *")
	public String runStatistics() {
		LocalDate targetDate = LocalDateTime.now().minusDays(1).toLocalDate();

		//일별 통계
		schedulerService.dailyStatistic(targetDate);
		//월별 통계
		schedulerService.monthlyStatistic(targetDate);

		return "Daily-statistic-OK";
	}
}
