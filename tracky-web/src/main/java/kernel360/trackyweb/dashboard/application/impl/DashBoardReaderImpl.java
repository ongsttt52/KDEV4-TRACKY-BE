package kernel360.trackyweb.dashboard.application.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import kernel360.trackyweb.dashboard.application.DashBoardReader;
import kernel360.trackyweb.dashboard.application.dto.response.RentDashboardDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashBoardReaderImpl implements DashBoardReader {

	public List<RentDashboardDto> findRentsByDate(LocalDate baseDate) {
		LocalDateTime start = baseDate.atStartOfDay();
		LocalDateTime end = baseDate.plusDays(1).atStartOfDay();

		return null;
/*		return dashRentRepository.findRentsOnDate(start, end).stream()
			.map(RentDashboardDto::from)
			.sorted(Comparator.comparing(RentDashboardDto::rentStime))
			.toList();*/
	}
}
