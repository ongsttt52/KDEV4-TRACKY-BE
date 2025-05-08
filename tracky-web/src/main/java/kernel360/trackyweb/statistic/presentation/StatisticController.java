package kernel360.trackyweb.statistic.presentation;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import kernel360.trackyweb.statistic.application.StatisticService;
import kernel360.trackyweb.statistic.application.dto.response.DailyStatisticResponse;
import kernel360.trackyweb.statistic.application.dto.response.MonthlyStatisticResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
@Slf4j
public class StatisticController {

	private final StatisticService statisticService;

	@GetMapping("/daily")
	public ApiResponse<DailyStatisticResponse> getDailyStatistic(
		@AuthenticationPrincipal MemberPrincipal memberPrincipal,
		@RequestParam(name = "date")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		LocalDate date
	) {
		return statisticService.getDailyStatistic(memberPrincipal.bizUuid(), date);
	}

	@GetMapping("/monthly")
	public ApiResponse<MonthlyStatisticResponse> getMonthlyStatistic(
		@AuthenticationPrincipal MemberPrincipal memberPrincipal,
		@RequestParam(name = "date")
		YearMonth date,
		YearMonth targetDate
	) {
		return statisticService.getMonthlyStatistic(memberPrincipal.bizUuid(), date, targetDate);
	}

	@GetMapping("/cars")
	public ApiResponse<List<CarStatisticResponse>> getCarStatistic(
		@AuthenticationPrincipal MemberPrincipal memberPrincipal,
		@ModelAttribute CarStatisticRequest carStatisticRequest
	) {
		return statisticService.getCarStatistic(memberPrincipal.bizUuid(), carStatisticRequest);
	}
}