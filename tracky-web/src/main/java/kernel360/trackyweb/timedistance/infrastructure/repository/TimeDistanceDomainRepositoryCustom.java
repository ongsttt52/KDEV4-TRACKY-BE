package kernel360.trackyweb.timedistance.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.core.Tuple;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;

public interface TimeDistanceDomainRepositoryCustom {

	List<OperationTime> getTotalOperationTimeGroupedByBIzId(LocalDate date);

	List<Tuple> countByBizIdAndDateGroupedByHour(Long bizId, LocalDate date);
}
