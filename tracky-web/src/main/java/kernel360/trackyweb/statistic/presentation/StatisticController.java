package kernel360.trackyweb.statistic.presentation;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import kernel360.trackyweb.statistic.application.StatisticService;
import kernel360.trackyweb.statistic.application.dto.response.DailyStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

	private final StatisticService statisticService;

	@GetMapping("/daily")
	public ApiResponse<DailyStatisticResponse> getDailyStatistic(
		@AuthenticationPrincipal MemberPrincipal memberPrincipal,
		@RequestParam("date")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate date
	) {
		return statisticService.getDailyStatistic(memberPrincipal.bizUuid(), date);
	}

	@GetMapping("/monthly")
	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(
		@AuthenticationPrincipal MemberPrincipal memberPrincipal,
		@RequestParam(name = "date")
		LocalDate date
	) {
		return statisticService.getMonthlyStatistic(memberPrincipal.bizUuid(), date);
	}
}