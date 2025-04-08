package kernel360.trackyweb.dashboard.application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackyweb.dashboard.domain.CarStatus;
import kernel360.trackyweb.dashboard.domain.DashBoardReader;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import kernel360.trackyweb.dashboard.infrastructure.components.ProvinceMatcher;
import kernel360.trackyweb.dashboard.infrastructure.repository.CarStatusRepository;
import kernel360.trackyweb.dashboard.infrastructure.repository.DashGpsHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashBoardService {

	private final DashBoardReader dashBoardReader;
	private final CarStatusRepository carStatusRepository;

	private final ProvinceMatcher provinceMatcher;

	private final DashGpsHistoryRepository dashGpsHistoryRepository;

	/**
	 * 예약 현황 조회( 어제/오늘/내일 )
	 * @param date
	 * @return
	 */
	public ApiResponse<List<RentDashboardDto>> findRents(String date) {
		LocalDate baseDate = switch (date.toLowerCase()) {
			case "yesterday" -> LocalDate.now().minusDays(1);
			case "tomorrow" -> LocalDate.now().plusDays(1);
			default -> LocalDate.now();
		};
		List<RentDashboardDto> rents = dashBoardReader.findRentsByDate(baseDate);
		return ApiResponse.success(rents);
	}

	/**
	 * 차량 상태 통계 api
	 * @return hashmap(status, count)
	 */
	public Map<String, Long> getAllCarStatus() {
		List<CarStatus> grouped = carStatusRepository.findAllGroupedByStatus();

		String provinceName = provinceMatcher.findProvince(127.03, 37.56);
		System.out.println("해당 좌표는 위치한 도: " + provinceName);  // 예: 서울특별시

		Map<String, Long> result = new HashMap<>();
		for (CarStatus carStatus : grouped) {
			result.put(carStatus.getStatus(), carStatus.getCount());
		}

		return result;
	}

	public Map<String, Integer> getGeoData() {
		List<GpsHistoryEntity> gpsList = dashGpsHistoryRepository.findLatestGpsByMdn();

		Map<String, Integer> provinceCountMap = new HashMap<>();

		for (GpsHistoryEntity gps : gpsList) {
			// DB에 저장된 위도/경도는 정수형이므로 소수로 변환 필요
			double lat = gps.getLat() / 1_000_000.0;
			double lon = gps.getLon() / 1_000_000.0;

			String province = provinceMatcher.findProvince(lon, lat);

			provinceCountMap.put(province, provinceCountMap.getOrDefault(province, 0) + 1);
		}
		return provinceCountMap;
	}
}
