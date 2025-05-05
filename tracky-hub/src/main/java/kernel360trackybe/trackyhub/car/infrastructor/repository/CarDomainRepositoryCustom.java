package kernel360trackybe.trackyhub.car.infrastructor.repository;

import java.util.List;

import kernel360.trackycore.core.domain.entity.CarEntity;

public interface CarDomainRepositoryCustom {

	List<CarEntity> availableRent();
}
