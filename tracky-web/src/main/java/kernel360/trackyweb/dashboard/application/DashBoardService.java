package kernel360.trackyweb.dashboard.application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.domain.CarStatus;
import kernel360.trackyweb.dashboard.domain.DashBoardReader;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import kernel360.trackyweb.dashboard.infrastructure.repository.CarStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashBoardService {

	private final DashBoardReader dashBoardReader;
	private final CarStatusRepository carStatusRepository;

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

		Map<String, Long> result = new HashMap<>();
		for (CarStatus carStatus : grouped) {
			result.put(carStatus.getStatus(), carStatus.getCount());
		}

		return result;
	}
}
