package kernel360.trackyweb.statistic.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackyweb.car.application.dto.internal.CarCountWithBizId;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.drive.application.dto.internal.OperationCarCount;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.statistic.domain.provider.DailyStatisticProvider;
import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;
import kernel360.trackyweb.timedistance.domain.provider.TimeDistanceDomainProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerService {

	private final DailyStatisticProvider dailyProvider;

	private final CarDomainProvider carDomainProvider;
	private final DriveDomainProvider driveDomainProvider;
	private final TimeDistanceDomainProvider timeDistanceDomainProvider;

	public void dailyStatistic(LocalDate targetDate) {
		//업체별 차량 총 개수
		List<CarCountWithBizId> carCountResult = carDomainProvider.coundAllCarGroupedByBizId();
		Map<String, Integer> totalCarMap = CarCountWithBizId.toMap(carCountResult);

		//일일 운행 차량 수
		List<OperationCarCount> operationCarCounts = driveDomainProvider.countOperationCarGroupedByBizId(targetDate);
		Map<String, Integer> operationCarMap = OperationCarCount.toMap(operationCarCounts);

		//일일 가동률
		Map<String, Double> operationRates = calculateOperationRate(totalCarMap, operationCarMap);

		//일일 총 운행 시간
		List<OperationTime> operationTimeList = timeDistanceDomainProvider.calculateOperationTimeGroupedByBizId(
			targetDate);
		Map<String, Integer> operationSeconds = new HashMap<>();

		//일일 총 운행 횟수

		//일일 총 운행 거리

		List<DailyStatisticEntity> resultEntities = new ArrayList<>();
		for (String bizId : totalCarMap.keySet()) {
			int totalCar = totalCarMap.getOrDefault(bizId, 0);
			int operating = operationCarMap.getOrDefault(bizId, 0);
			double operationRate = operationRates.getOrDefault(bizId, 0.0);
			// long operationSeconds = driveSecondsMap.getOrDefault(bizId, 0L);
			// int count = driveCountMap.getOrDefault(bizId, 0);
			// double distance = driveDistanceMap.getOrDefault(bizId, 0.0);

			// DailyStatisticEntity stat = DailyStatisticEntity.create(bizId, targetDate, totalCar,
			// 	operating, operationRate, operationSeconds,
			// 	count, distance);
			// resultEntities.add(stat);
		}

		// 3. 저장
		dailyProvider.saveDailyStatistic(resultEntities);
	}

	//가동률 계산
	private Map<String, Double> calculateOperationRate(Map<String, Integer> totalCarMap,
		Map<String, Integer> operationCarMap) {
		Map<String, Double> result = new HashMap<>();

		// totalCarMap의 모든 bizId에 대해 반복
		for (Map.Entry<String, Integer> entry : totalCarMap.entrySet()) {
			String bizId = entry.getKey();
			int totalCarCount = entry.getValue();

			// 해당 bizId에 대한 운행 차량 수 가져오기 (없으면 0)
			int operationCarCount = operationCarMap.getOrDefault(bizId, 0);

			// 가동률 계산 (총 차량이 0인 경우 0%로 설정)
			double operationRate = totalCarCount > 0 ? (double)operationCarCount / totalCarCount * 100 : 0;

			result.put(bizId, operationRate);
		}
		return result;
	}
}
