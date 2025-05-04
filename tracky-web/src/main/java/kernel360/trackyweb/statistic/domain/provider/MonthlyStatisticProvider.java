package kernel360.trackyweb.statistic.domain.provider;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.Tuple;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.statistic.infrastructure.repository.MonthlyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyStatisticProvider {

	private final MonthlyStatisticDomainRepository monthlyStatisticRepository;

	public MonthlyStatisticEntity getMonthlyStatistic(Long bizId, LocalDate date) {

		return monthlyStatisticRepository.findByBizIdAndDate(bizId, date)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.STATISTIC_NOT_FOUND));
	}

	public List<Tuple> getMonthlyDataTuples(Long bizId, LocalDate localDate, LocalDate localDate1) {

		return monthlyStatisticRepository.getMonthlyDataTuples(bizId, localDate, localDate1);
	}
}
