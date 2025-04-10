package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import kernel360.trackyweb.dashboard.domain.DashBoardReader;
import kernel360.trackyweb.dashboard.domain.RentDashboardDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashBoardReaderImpl implements DashBoardReader {

	private final DashRentRepository dashRentRepository;
	private final DashCarRepository dashCarRepository;

	public List<RentDashboardDto> findRentsByDate(LocalDate baseDate) {
		LocalDateTime start = baseDate.atStartOfDay();
		LocalDateTime end = baseDate.plusDays(1).atStartOfDay();

		return dashRentRepository.findRentsOnDate(start, end).stream()
			.map(RentDashboardDto::from)
			.sorted(Comparator.comparing(RentDashboardDto::rentStime))
			.toList();
	}
}
