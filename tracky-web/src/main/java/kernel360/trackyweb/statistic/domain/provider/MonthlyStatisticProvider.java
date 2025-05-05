package kernel360.trackyweb.statistic.domain.provider;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.statistic.domain.dto.MonthlyStat;
import kernel360.trackyweb.statistic.domain.dto.Summary;
import kernel360.trackyweb.statistic.infrastructure.repository.MonthlyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyStatisticProvider {

	private final MonthlyStatisticDomainRepository monthlyStatisticRepository;

	public Summary getSummary(Long bizId, YearMonth date) {

		MonthlyStatisticEntity monthlyStatistic = monthlyStatisticRepository.findByBizIdAndDate(bizId, date.atDay(1))
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.STATISTIC_NOT_FOUND));

		return Summary.from(monthlyStatistic);
	}

	public List<MonthlyStat> getMonthlyStats(Long bizId, YearMonth currentDate, YearMonth targetDate) {

		if (targetDate == null) {
			targetDate = currentDate.minusYears(1).plusMonths(1);
		}

		return monthlyStatisticRepository.getMonthlyStats(bizId, currentDate.atDay(1), targetDate.atDay(1));
	}
}
