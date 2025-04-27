package kernel360trackybe.trackybatch.job.daily.carData;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360trackybe.trackybatch.job.daily.carData.dto.DailyCarData;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CarCountProcessor implements ItemProcessor<DailyCarData, DailyStatisticEntity> {

	@Override
	public DailyStatisticEntity process(DailyCarData item) throws Exception {
		log.info("Processing car data for bizUuid: {}", item.getBizUuid());

		// 가동률 계산
		double utilizationRate = 0.0;
		if (item.getTotalCarCount() > 0) {
			utilizationRate = (item.getOperatingCarCount() / (double)item.getTotalCarCount()) * 100;
		}

		// DailyStatisticEntity 생성
		DailyStatisticEntity stats = new DailyStatisticEntity();
		stats.setBizUuid(item.getBizUuid());
		stats.setDate(item.getDate());
		stats.setTotalCarCount(item.getTotalCarCount());
		stats.setDailyDriveCarCount(item.getOperatingCarCount());
		stats.setAvgOperationRate(utilizationRate);

		// 다른 필드는 아직 계산하지 않았으므로 null로 설정
		stats.setDailyDriveSec(null);
		stats.setDailyDriveCount(null);
		stats.setDailyDriveDistance(null);

		log.info("Created DailyStatisticEntity: totalCars={}, operatingCars={}, utilizationRate={}%",
			item.getTotalCarCount(), item.getOperatingCarCount(), utilizationRate);

		return stats;
	}
}
