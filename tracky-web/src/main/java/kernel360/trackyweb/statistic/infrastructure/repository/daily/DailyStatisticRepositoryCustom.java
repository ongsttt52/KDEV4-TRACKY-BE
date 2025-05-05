package kernel360.trackyweb.statistic.infrastructure.repository.daily;

import kernel360.trackyweb.statistic.application.dto.internal.*;

import java.time.LocalDate;
import java.util.List;

public interface DailyStatisticRepositoryCustom {

    List<TotalCarCount> getLastTotalCarCount(LocalDate targetDate);

    List<OperationRate> findAverageOperationRate(LocalDate targetDate);

    List<OperationCount> findSumOperationCount(LocalDate targetDate);

    List<OperationTime> findSumOperationTime(LocalDate targetDate);

    List<OperationDistance> findSumOperationDistance(LocalDate targetDate);
}
