package kernel360.trackyconsumer.timedistance.domain.provider;

import java.time.LocalDate;
import java.util.Optional;
import kernel360.trackyconsumer.timedistance.infrastructure.repository.TimeDistanceDomainRepository;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.TimeDistanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeDistanceDomainProvider {

	private final TimeDistanceDomainRepository timeDistanceDomainRepository;

	public void save(TimeDistanceEntity timeDistance) {
		timeDistanceDomainRepository.save(timeDistance);
	}

	public Optional<TimeDistanceEntity> getTimeDistance(LocalDate date, int hour, CarEntity car) {
		return timeDistanceDomainRepository.findFirstByDateAndHourAndCar(date, hour, car);
	}
}
