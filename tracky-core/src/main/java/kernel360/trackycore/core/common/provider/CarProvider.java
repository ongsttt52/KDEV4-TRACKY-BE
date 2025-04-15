package kernel360.trackycore.core.common.provider;

import java.util.Optional;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.infrastructure.repository.CarRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarProvider {

	private final CarRepository carRepository;

	// save and update
	public CarEntity saveCar(CarEntity car) {
		return carRepository.save(car);
	}

	public Optional<CarEntity> findByMdn(String mdn) {
		return carRepository.findByMdn(mdn);
	}

	public boolean existsByMdn(String mdn) {
		return carRepository.existsByMdn(mdn);
	}
}
