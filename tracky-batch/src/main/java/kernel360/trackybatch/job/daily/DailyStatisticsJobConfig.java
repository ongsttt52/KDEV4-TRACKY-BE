package kernel360.trackybatch.job.daily;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DailyStatisticsJobConfig {

	private static final Logger log = LoggerFactory.getLogger(DailyStatisticsJobConfig.class);

	private final JobRepository jobRepository;
	private final Step dailyCarStatsStep;

	@Bean
	public Job dailyStatisticsJob() {
		log.info("Creating DailyStatisticsJob with CarDataStepConfig");

		Job job = new JobBuilder("dailyCarStatsJob", jobRepository)
			.start(dailyCarStatsStep)
			.build();

		log.info("DailyStatisticsJob created successfully");

		return job;
	}
}
