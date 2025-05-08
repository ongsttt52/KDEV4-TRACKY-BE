package kernel360.trackyweb.timedistance.domain.provider;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Component;

import kernel360.trackyweb.timedistance.application.dto.internal.OperationDistance;
import kernel360.trackyweb.timedistance.application.dto.internal.OperationSeconds;
import kernel360.trackyweb.timedistance.infrastructure.repository.TimeDistanceDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimeDistanceDomainProvider {

	private final TimeDistanceDomainRepository timeDistanceDomainRepository;

	public Map<Long, Long> calculateDailyOperationTime(LocalDate targetDate) {
		return OperationSeconds.toMap(timeDistanceDomainRepository.getDailyOperationTime(targetDate));
	}

	public Map<Long, Double> calculateDailyOperationDistance(LocalDate targetDate) {
		return OperationDistance.toMap(timeDistanceDomainRepository.getDailyOperationDistance(targetDate));
	}
}
