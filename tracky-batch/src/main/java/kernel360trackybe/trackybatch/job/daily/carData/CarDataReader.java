package kernel360trackybe.trackybatch.job.daily.carData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

import kernel360trackybe.trackybatch.infrastructure.CarBatchRepository;
import kernel360trackybe.trackybatch.infrastructure.DriveBatchRepository;
import kernel360trackybe.trackybatch.job.daily.carData.dto.DailyCarData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarDataReader implements ItemReader<DailyCarData> {

	private CarBatchRepository carBatchRepository;
	private DriveBatchRepository driveBatchRepository;

	@Value("#{jobParameters['targetDate']}")
	private LocalDateTime targetDate;

	private List<DailyCarData> companyDataList = new ArrayList<>();
	private int currentIndex = 0;
	private boolean dataLoaded = false;

	public CarDataReader(
		CarBatchRepository carBatchRepository,
		DriveBatchRepository driveBatchRepository,
		LocalDateTime targetDate) {
		this.carBatchRepository = carBatchRepository;
		this.driveBatchRepository = driveBatchRepository;
		this.targetDate = targetDate;
		this.companyDataList = new ArrayList<>();
	}

	@Override
	public DailyCarData read() {
		// 최초 실행 시 모든 업체 데이터 로드
		if (!dataLoaded) {
			loadAllCompanyData();
			dataLoaded = true;
		}

		// 아직 처리할 데이터가 남아있으면 반환
		if (currentIndex < companyDataList.size()) {
			log.info("Returning company data at index {}: {}", currentIndex, companyDataList.get(currentIndex));
			return companyDataList.get(currentIndex++);
		}

		log.info("No more company data to process, returning null");
		return null; // 더 이상 데이터가 없으면 null 반환
	}

	private void loadAllCompanyData() {
		log.info("Loading all company data for targetDate: {}", targetDate);

		// 전체 차량 수 조회
		List<Object[]> totalCarCountList = carBatchRepository.countByBizUuidGrouped();
		log.info("Total car count by company: {}", totalCarCountList);

		// 운행 차량 수 조회
		LocalDateTime startDateTime = targetDate;
		LocalDateTime endDateTime = targetDate.plusDays(1);
		List<Object[]> operatingCarCountList =
			driveBatchRepository.countDailyOperatingCarsGroupedByBizUuid(startDateTime, endDateTime);
		log.info("Operating car count by company: {}", operatingCarCountList);

		// 운행 차량 데이터를 맵으로 변환하여 조회 성능 향상
		Map<String, Integer> operatingCarMap = new HashMap<>();
		for (Object[] operatingData : operatingCarCountList) {
			String bizUuid = (String)operatingData[0];
			int count = convertToInt(operatingData[1]);
			operatingCarMap.put(bizUuid, count);
		}

		// 각 업체별로 데이터 생성
		for (Object[] totalCarData : totalCarCountList) {
			String bizUuid = (String)totalCarData[0];
			int totalVehicles = convertToInt(totalCarData[1]);

			// 해당 업체의 운행 차량 수 조회
			int operatingVehicles = operatingCarMap.getOrDefault(bizUuid, 0);

			// 업체별 데이터 객체 생성
			DailyCarData companyData = new DailyCarData();
			companyData.setBizUuid(bizUuid);
			companyData.setDate(targetDate);
			companyData.setTotalCarCount(totalVehicles);
			companyData.setOperatingCarCount(operatingVehicles);

			// 리스트에 추가
			companyDataList.add(companyData);
			log.info("Added company data: {}", companyData);
		}

		log.info("Loaded {} company data items", companyDataList.size());
	}

	// Long을 int로 변환하는 헬퍼 메서드
	private int convertToInt(Object value) {
		if (value instanceof Long) {
			return ((Long)value).intValue();
		} else if (value instanceof Integer) {
			return (Integer)value;
		}
		return 0;
	}
}
