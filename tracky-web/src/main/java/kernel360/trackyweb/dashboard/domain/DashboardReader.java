package kernel360.trackyweb.dashboard.domain;

import java.time.LocalDate;
import java.util.List;

public interface DashboardReader {
	List<RentDashboardDto> findRentsByDate(LocalDate date);
}