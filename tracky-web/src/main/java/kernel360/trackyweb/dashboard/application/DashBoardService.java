package kernel360.trackyweb.dashboard.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackyweb.dashboard.domain.DashboardReader;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashBoardService {

	private final DashboardReader dashboardReader;

	public List<RentDashboardDto> getRentsByDate(LocalDate date) {
		return DashboardReader.findRentsByDate(date);
	}
}
