package kernel360.trackyweb.dashboard.application;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackyweb.dashboard.application.dto.response.RentDashboardDto;

public interface DashBoardReader {
	List<RentDashboardDto> findRentsByDate(LocalDate baseDate);
}
