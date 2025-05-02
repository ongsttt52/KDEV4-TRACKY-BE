package kernel360.trackyweb.timedistance.domain.provider;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationTime;
import kernel360.trackyweb.timedistance.infrastructure.repository.TimeDistanceDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimeDistanceDomainProvider {
	TimeDistanceDomainRepository timeDistanceDomainRepository;

	public List<OperationTime> calculateOperationTimeGroupedByBizId(LocalDate targetDate) {
		return timeDistanceDomainRepository.getTotalOperationTimeGroupedByBIzId(targetDate);
	}
}
