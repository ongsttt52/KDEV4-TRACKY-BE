package kernel360trackybe.trackyhub.infrastructor.repository;

import java.util.List;

import kernel360.trackycore.core.domain.entity.CarEntity;

public interface CarHubDomainRepositoryCustom {

	List<CarEntity> availableRent();
}
