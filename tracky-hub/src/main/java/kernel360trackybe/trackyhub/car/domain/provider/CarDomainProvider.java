package kernel360trackybe.trackyhub.car.domain.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360trackybe.trackyhub.car.infrastructor.repository.CarDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarDomainProvider {

	private final CarDomainRepository carDomainRepository;

	public List<CarEntity> findAllByAvailableRent() {
		return carDomainRepository.availableRent();
	}
}
