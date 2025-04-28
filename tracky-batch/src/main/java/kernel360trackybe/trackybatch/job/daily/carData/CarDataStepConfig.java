package kernel360trackybe.trackybatch.job.daily.carData;

import java.time.LocalDateTime;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360trackybe.trackybatch.infrastructure.CarBatchRepository;
import kernel360trackybe.trackybatch.infrastructure.DriveBatchRepository;
import kernel360trackybe.trackybatch.job.daily.carData.dto.DailyCarData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CarDataStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final CarCountProcessor carCountProcessor;
	private final CarCountWriter carCountWriter;

	private final CarBatchRepository carBatchRepository;
	private final DriveBatchRepository driveBatchRepository;

	@Bean
	public Step dailyCarStatsStep(CarDataReader carDataReader) {
		log.info("Creating dailyCarStatsStep with CarDataReader, CarCountProcessor, and CarCountWriter");

		Step step = new StepBuilder("dailyCarStatsStep", jobRepository)
			.<DailyCarData, DailyStatisticEntity>chunk(10, transactionManager)
			.reader(carDataReader)
			.processor(carCountProcessor)
			.writer(carCountWriter)
			.build();

		log.info("DailyCarStatsStep created successfully");
		return step;
	}

	@Bean
	@StepScope
	public CarDataReader carDataReader(
		@Value("#{jobParameters['targetDate']}") String targetDateStr
	) {

		log.info("Creating CarDataReader with targetDate: {}", targetDateStr);
		LocalDateTime targetDate = LocalDateTime.parse(targetDateStr);

		return new CarDataReader(
			carBatchRepository,
			driveBatchRepository,
			targetDate
		);
	}
}
