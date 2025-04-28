package kernel360.trackyconsumer.timedistance.infrastructure.repository;

import java.time.LocalDate;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.TimeDistanceEntity;

public interface TimeDistanceRepositoryCustom {

	public TimeDistanceEntity findByDateAndHourAndCar(LocalDate date, int hour, CarEntity car);
}
