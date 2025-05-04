package kernel360.trackyweb.statistic.domain.provider;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;
import kernel360.trackyweb.statistic.infrastructure.repository.DailyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DailyStatisticProvider {

	private final DailyStatisticDomainRepository dailyStatisticDomainRepository;

	public void saveDailyStatistic(List<DailyStatisticEntity> resultEntities) {
		dailyStatisticDomainRepository.saveAll(resultEntities);
	}

	public DailyStatisticEntity getDailyStatistic(Long bizId, LocalDate date) {

		return dailyStatisticDomainRepository.findByBizIdAndDate(bizId, date)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.STATISTIC_NOT_FOUND));
	}
}
