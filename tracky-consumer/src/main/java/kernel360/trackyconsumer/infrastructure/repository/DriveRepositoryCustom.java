package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;

public interface DriveRepositoryCustom {
	Optional<DriveEntity> getDrive(CarEntity car, LocalDateTime otime);
}
