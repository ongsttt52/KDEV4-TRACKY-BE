package kernel360.trackyweb.car.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.infrastructure.entity.CarEntity;
import kernel360.trackyweb.car.domain.CarResponse;
import kernel360.trackyweb.car.infrastructure.repository.CarRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {

	private final CarRepository carRepository;

	public List<CarResponse> getAll() {
		return carRepository.findAll().stream()
			.map(CarResponse::from)
			.collect(Collectors.toList());
	}

	public List<CarResponse> searchByMdn(String keyword) {
		return carRepository.findByMdnContainingOrdered(keyword).stream()
			.map(CarResponse::from)
			.collect(Collectors.toList());
	}
}
