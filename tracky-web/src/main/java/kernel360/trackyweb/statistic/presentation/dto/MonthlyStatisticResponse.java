package kernel360.trackyweb.statistic.presentation.dto;

import java.util.List;

import kernel360.trackyweb.statistic.domain.dto.MonthlyStat;
import kernel360.trackyweb.statistic.domain.dto.Summary;

public record MonthlyStatisticResponse(
	Summary summary,
	List<MonthlyStat> monthlyStats
) {
}
