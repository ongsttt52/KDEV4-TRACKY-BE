package kernel360.trackycore.core.domain.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.LocationEntity;
import kernel360.trackycore.core.infrastructure.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationProvider {

	private final LocationRepository locationRepository;

	public LocationEntity save(LocationEntity location) {
		return locationRepository.save(location);
	}
}
