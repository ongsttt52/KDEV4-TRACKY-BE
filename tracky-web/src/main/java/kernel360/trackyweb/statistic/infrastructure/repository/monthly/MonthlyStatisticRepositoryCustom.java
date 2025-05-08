package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public interface MonthlyStatisticRepositoryCustom {

	MonthlyStatisticEntity findLatestMonthlyStatistic(String bizUuid);

}
