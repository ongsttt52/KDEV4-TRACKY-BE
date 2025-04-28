package kernel360trackybe.trackybatch.presentation;

import java.time.LocalDateTime;

import org.springframework.batch.core.JobExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360trackybe.trackybatch.service.CarStatisticJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
@Slf4j
public class BatchController {
	private final CarStatisticJobService carStatisticJobService;

	@PostMapping("/daily-statistic")
	public ResponseEntity<String> runCarStatisticsJob(
		@RequestParam LocalDateTime targetDate) {
		log.info("car statistics job");

		try {
			JobExecution execution = carStatisticJobService.runCarStatsJob(targetDate);
			return ResponseEntity.ok("Daily Job started with ID: " + execution.getJobId() +
				", Status: " + execution.getStatus());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Failed to start job: " + e.getMessage());
		}
	}

}
