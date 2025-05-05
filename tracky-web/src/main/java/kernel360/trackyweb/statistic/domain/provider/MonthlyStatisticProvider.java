package kernel360.trackyweb.statistic.domain.provider;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;
import kernel360.trackyweb.statistic.infrastructure.repository.monthly.MonthlyStatisticDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyStatisticProvider {

    private final MonthlyStatisticDomainRepository monthlyStatisticRepository;

    public MonthlyStatisticEntity getMonthlyStatistic(Long bizId, LocalDate date) {

        return monthlyStatisticRepository.findByBizIdAndDate(bizId, date)
                .orElseThrow(() -> GlobalException.throwError(ErrorCode.STATISTIC_NOT_FOUND));
    }

    public void saveMonthlyStatistic(List<MonthlyStatisticEntity> resultEntities) {
        monthlyStatisticRepository.saveAll(resultEntities);
    }
}
