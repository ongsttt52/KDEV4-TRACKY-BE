package kernel360trackybe.trackybatch.service;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarStatisticJobService {

	private final JobLauncher jobLauncher;
	private final Job dailyStatisticsJob;

	public JobExecution runCarStatsJob(LocalDateTime targetDate) {
		log.info("Running car statistics job for bizUuid: {} and targetDate: {}", targetDate);

		try {
			JobParameters jobParameters = new JobParametersBuilder()
				.addString("targetDate", targetDate.toString())
				.addLong("time", System.currentTimeMillis())
				.toJobParameters();

			log.info("Job parameters:  targetDate={}", targetDate);

			// Execute the job
			JobExecution execution = jobLauncher.run(dailyStatisticsJob, jobParameters);
			log.info("Job execution started with ID: {}, Status: {}", execution.getJobId(), execution.getStatus());

			return execution;
		} catch (Exception e) {
			log.error("Failed to run vehicle stats job for bizUuid: {} and targetDate: {}", targetDate, e);
			throw new RuntimeException("Failed to run vehicle stats job", e);
		}
	}
}
