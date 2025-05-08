package kernel360.trackyweb.statistic.infrastructure.repository.monthly;

import kernel360.trackyweb.statistic.application.dto.internal.DashboardStatistic;

public interface MonthlyStatisticRepositoryCustom {

	DashboardStatistic findStatisticReportByBizUuid(String bizUuid);

}
