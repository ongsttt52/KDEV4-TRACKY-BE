package kernel360trackybe.trackyhub.application.domain.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360trackybe.trackyhub.infrastructor.repository.CarHubDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarHubDomainProvider {

	private final CarHubDomainRepository carHubDomainRepository;

	public List<CarEntity> findAllByAvailableRent() {
		return carHubDomainRepository.availableRent();
	}
}
