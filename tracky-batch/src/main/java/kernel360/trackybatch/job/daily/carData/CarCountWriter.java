package kernel360.trackybatch.job.daily.carData;

import java.util.Optional;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackybatch.infrastructure.DailyStatisticsRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarCountWriter implements ItemWriter<DailyStatisticEntity> {

	private final DailyStatisticsRepository dailyStatisticsRepository;

	@Override
	public void write(Chunk<? extends DailyStatisticEntity> chunk) throws Exception {
		for (DailyStatisticEntity newStats : chunk.getItems()) {
			// BizUuid와 날짜를 기준으로 해당 데이터가 존재하는지 확인
			Optional<DailyStatisticEntity> existingStats = dailyStatisticsRepository.findByBizUuidAndDate(
				newStats.getBizUuid(), newStats.getDate());

			// 데이터가 존재하지 않으면 새로 저장
			if (existingStats.isEmpty()) {
				dailyStatisticsRepository.save(newStats);
			} else {
				// 데이터가 존재하면 업데이트 처리
				DailyStatisticEntity statsToUpdate = existingStats.get();
				statsToUpdate.setTotalCarCount(newStats.getTotalCarCount());
				statsToUpdate.setDailyDriveCarCount(newStats.getDailyDriveCarCount());
				statsToUpdate.setAvgOperationRate(newStats.getAvgOperationRate());

				dailyStatisticsRepository.save(statsToUpdate);
			}
		}
	}
}
