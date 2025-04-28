package kernel360.trackyweb.statistic.presentation;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.statistic.application.StatisticService;
import kernel360.trackyweb.statistic.application.dto.response.DailyStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

	private final StatisticService statisticService;

	@GetMapping("{bizUuid}/daily")
	public ApiResponse<DailyStatisticResponse> getDailyStatistic(
		@PathVariable UUID bizUuid,
		@RequestParam("date")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate date
	) {
		return statisticService.getDailyStatistic(bizUuid, date);
	}

	@GetMapping("{bizUuid}/monthly")
	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(
		@PathVariable UUID bizUuid,
		@RequestParam("date")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate date
	) {
		return statisticService.getMonthlyStatistic(bizUuid, date);
	}
}
