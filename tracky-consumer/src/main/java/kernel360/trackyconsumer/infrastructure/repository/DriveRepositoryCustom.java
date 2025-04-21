package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;

public interface DriveRepositoryCustom {
	Optional<DriveEntity> getDrive(CarEntity car, LocalDateTime otime);
}
