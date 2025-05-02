package kernel360.trackyweb.statistic.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kernel360.trackyweb.statistic.application.SchedulerService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticScheduler {
	private final SchedulerService schedulerService;

	public void runDailyStatistic() {
		LocalDate targetDate = LocalDateTime.now().minusDays(1).toLocalDate();
		schedulerService.dailyStatistic(targetDate);
	}

}
