package kernel360.trackyweb.timedistance.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationDistance;
import kernel360.trackyweb.timedistance.application.dto.internal.OperationSeconds;
import com.querydsl.core.Tuple;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;

public interface TimeDistanceDomainRepositoryCustom {
	List<OperationSeconds> getDailyOperationTime(LocalDate targetDate);

	List<OperationTime> getTotalOperationTimeGroupedByBIzId(LocalDate targetDate);

	List<OperationDistance> getDailyOperationDistance(LocalDate targetDate);

	List<Tuple> countByBizIdAndDateGroupedByHour(Long bizId, LocalDate targetDate);
}
