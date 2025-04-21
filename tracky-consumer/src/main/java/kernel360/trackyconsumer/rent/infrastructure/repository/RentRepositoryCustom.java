package kernel360.trackyconsumer.rent.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;

public interface RentRepositoryCustom {

	Optional<RentEntity> getRent(CarEntity car, LocalDateTime carOnTime);
}
