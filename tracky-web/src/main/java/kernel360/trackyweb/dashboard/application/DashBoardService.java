package kernel360.trackyweb.dashboard.application;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;
import kernel360.trackycore.core.domain.provider.RentProvider;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.dashboard.application.dto.response.DashboardCarStatusResponse;
import kernel360.trackyweb.dashboard.application.dto.response.DashboardReturnResponse;
import kernel360.trackyweb.dashboard.application.dto.response.DashboardStatisticsResponse;
import kernel360.trackyweb.dashboard.domain.provider.DashGpsHistoryProvider;
import kernel360.trackyweb.dashboard.infrastructure.components.ProvinceMatcher;
import kernel360.trackyweb.rent.domain.provider.RentDomainProvider;
import kernel360.trackyweb.statistic.domain.provider.DailyStatisticProvider;
import kernel360.trackyweb.statistic.domain.provider.MonthlyStatisticProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashBoardService {
	private final DashGpsHistoryProvider dashGpsHistoryProvider;
	private final CarDomainProvider carDomainProvider;
	private final RentProvider rentProvider;
	private final RentDomainProvider rentDomainProvider;

	private final ProvinceMatcher provinceMatcher;
	private final MonthlyStatisticProvider monthlyStatisticProvider;
	private final DailyStatisticProvider dailyStatisticProvider;

	/**
	 * 반납 조회( 지연된 반납 )
	 *
	 * @param
	 * @return 지연된 반납 list
	 */
	public ApiResponse<List<DashboardReturnResponse>> getDelayedReturn(String bizUuid) {
		LocalDateTime now = LocalDateTime.now();
		List<RentEntity> delayedRents = rentDomainProvider.findDelayedRentList(bizUuid, now);

		return ApiResponse.success(
			delayedRents.stream()
				.map(rent -> {
					var car = rent.getCar();
					return new DashboardReturnResponse(
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
	 * 대시보드용 통계 데이터
	 *
	 * @return Statistics 통계 데이터
	 */
	@Transactional(readOnly = true)
	public DashboardStatisticsResponse getStatistics(String bizUuid) {
		MonthlyStatisticEntity monthlyStatisticEntity = monthlyStatisticProvider.getDashBoardStatistic(bizUuid);
		List<Integer> dailyDriveCount = dailyStatisticProvider.getDailyDriveCount(bizUuid);

		return DashboardStatisticsResponse.create(monthlyStatisticEntity.getAvgOperationRate(),
			monthlyStatisticEntity.getNonOperatingCarCount(),
			monthlyStatisticEntity.getTotalDriveCount(), dailyDriveCount);
	}

	/**
	 * geo 기반 영역 안의 차량 수 hashmap 구하기
	 *
	 * @return 구역 : 차량 수 map
	 */
	@Transactional(readOnly = true)
	public Map<String, Integer> getGeoData() {
		List<GpsHistoryEntity> gpsList = dashGpsHistoryProvider.findLatestGpsByMdn();

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
	}

	@Transactional
	public ApiResponse<String> updateStatusToReturn(String rentUuid) {

		RentEntity rent = rentProvider.getRent(rentUuid);

		rent.updateStatus(RentStatus.RETURNED);

		return ApiResponse.success("반납 처리 완료");
	}

	public List<DashboardCarStatusResponse> getAllCarStatus(String bizUuid) {
		return carDomainProvider.getCarStatusCounts(bizUuid);
	}
}
