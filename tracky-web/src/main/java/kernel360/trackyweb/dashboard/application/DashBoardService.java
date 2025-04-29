package kernel360.trackyweb.dashboard.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.provider.CarProvider;
import kernel360.trackycore.core.domain.provider.RentProvider;
import kernel360.trackyweb.dashboard.application.dto.response.ReturnResponse;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.rent.domain.provider.RentDomainProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashBoardService {
	/*

		private final DashBoardReader dashBoardReader;
		private final CarStatusRepository carStatusRepository;
		private final DashDriveRepository dashDriveRepository;
		//private final DashCarRepository dashCarRepository;
		private final DashRentRepository dashRentRepository;

		private final RentProvider rentProvider;
		private final CarProvider carProvider;
		private final DriveDomainProvider driveDomainProvider;

		private final ProvinceMatcher provinceMatcher;

	*/
	private final RentProvider rentProvider;
	private final CarProvider carProvider;
	private final RentDomainProvider rentDomainProvider;
	private final DriveDomainProvider driveDomainProvider;

	/**
	 * 예약 현황 조회( 어제/오늘/내일 )
	 * @param date
	 * @return 예약 list
	 */
/*	@Transactional(readOnly = true)
	public ApiResponse<List<RentDashboardDto>> findRents(String date) {
		LocalDate baseDate = switch (date.toLowerCase()) {
			case "yesterday" -> LocalDate.now().minusDays(1);
			case "tomorrow" -> LocalDate.now().plusDays(1);
			default -> LocalDate.now();
		};
		List<RentDashboardDto> rents = dashBoardReader.findRentsByDate(baseDate);
		return ApiResponse.success(rents);
	}*/

	/**
	 * 반납 조회( 지연된 반납 )
	 * @param
	 * @return 지연된 반납 list
	 */

	public ApiResponse<List<ReturnResponse>> getDelayedReturn(String bizUuid) {
		LocalDateTime now = LocalDateTime.now();
		List<RentEntity> delayedRents = rentDomainProvider.findDelayedRentList(bizUuid, now);

		return ApiResponse.success(
			delayedRents.stream()
				.map(rent -> {
					var car = rent.getCar();
					return new ReturnResponse(
						rent.getRentUuid(),
						rent.getRenterName(),
						rent.getRentStatus(),
						rent.getRentEtime(),
						car.getMdn(),
						car.getCarPlate(),
						car.getCarType()
					);
				})
				.collect(Collectors.toList())
		);
	}

	/**
	 * 차량 상태 통계 api
	 * @return hashmap(status, count)
	 */
/*	@Transactional(readOnly = true)
	public Map<String, Long> getAllCarStatus() {
		List<CarStatus> grouped = carStatusRepository.findAllGroupedByStatus();

		Map<String, Long> carStatusMap = new HashMap<>();
		for (CarStatus carStatus : grouped) {
			carStatusMap.put(carStatus.getStatus(), carStatus.getCount());
		}

		return carStatusMap;
	}*/

	/**
	 * 대시보드용 통계 데이터
	 * @return Statistics 통계 데이터
	 */
/*	@Transactional(readOnly = true)
	public Statistics getStatistics() {
		double totalDriveDistance = Optional.ofNullable(dashDriveRepository.getTotalDriveDistance()).orElse(0.0);
		long totalRentCount = dashRentRepository.count();
		long totalCarCount = carProvider.count();
		long totalRentDuration = Optional.ofNullable(dashRentRepository.getTotalRentDurationInMinutes()).orElse(0L);
		long totalDriveDuration = Optional.ofNullable(dashDriveRepository.getTotalDriveDurationInMinutes()).orElse(0L);

		return Statistics.create(totalDriveDistance, totalRentCount, totalCarCount, totalRentDuration,
			totalDriveDuration);
	}*/

	/**
	 * geo 기반 영역 안의 차량 수 hashmap 구하기
	 * @return 구역 : 차량 수 map
	 */
	/*@Transactional(readOnly = true)
	public Map<String, Integer> getGeoData() {
		List<GpsHistoryEntity> gpsList = dashGpsHistoryRepository.findLatestGpsByMdn();

		log.info("gpsList: {} ", gpsList);

		Map<String, Integer> provinceCountMap = new HashMap<>();

		for (GpsHistoryEntity gps : gpsList) {
			// DB에 저장된 위도/경도는 정수형이므로 소수로 변환 필요
			double lat = gps.getLat() / 1_000_000.0;
			double lon = gps.getLon() / 1_000_000.0;

			String province = provinceMatcher.findProvince(lon, lat);

			provinceCountMap.put(province, provinceCountMap.getOrDefault(province, 0) + 1);
		}
		log.info("provinceCountMap: {}", provinceCountMap);
		return provinceCountMap;
	}*/
}
