package kernel360.trackyweb.car.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.infrastructure.entity.CarEntity;
import kernel360.trackycore.core.infrastructure.exception.CarNotFoundException;
import kernel360.trackyweb.car.application.dto.CarDetailResponse;
import kernel360.trackyweb.car.application.dto.CarResponse;
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

	public CarResponse searchById(Long id) {
		CarEntity car = carRepository.findById(id)
			// 천승준 - 공통 에러 처리 해봤어요
			.orElseThrow(() -> new CarNotFoundException());
		return CarResponse.from(car);
	}

	public CarDetailResponse searchDetailById(Long id) {
		CarEntity car = carRepository.findDetailById(id)
			// 천승준 - 공통 에러 처리 해봤어요
			.orElseThrow(() -> new CarNotFoundException());
		return CarDetailResponse.from(car);
	}
}
