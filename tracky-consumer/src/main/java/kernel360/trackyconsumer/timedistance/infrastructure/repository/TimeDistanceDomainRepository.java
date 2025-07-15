package kernel360.trackyconsumer.timedistance.infrastructure.repository;

import java.time.LocalDate;
import java.util.Optional;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.TimeDistanceEntity;
import kernel360.trackycore.core.infrastructure.repository.TimeDistanceRepository;

public interface TimeDistanceDomainRepository extends TimeDistanceRepository, TimeDistanceRepositoryCustom {
    Optional<TimeDistanceEntity> findFirstByDateAndHourAndCar(LocalDate date, int hour, CarEntity car);
}