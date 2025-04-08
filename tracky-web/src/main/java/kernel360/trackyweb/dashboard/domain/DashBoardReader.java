package kernel360.trackyweb.dashboard.domain;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackycore.core.common.api.ApiResponse;

public interface DashBoardReader {
	List<RentDashboardDto> findRentsByDate(LocalDate baseDate);
}
