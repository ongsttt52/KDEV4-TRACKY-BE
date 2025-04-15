package kernel360.trackycore.core.common;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.sse.GlobalSseEvent;
import kernel360.trackycore.core.common.sse.SseEvent;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackyweb.car.infrastructure.repo.CarModuleRepository;
import kernel360.trackyweb.emitter.EventEmitterService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarProvider {

	private final CarModuleRepository carModuleRepository;

	private final GlobalSseEvent globalSseEvent;
	private final EventEmitterService eventEmitterService;

	public CarEntity getCar(String mdn) {
		return carModuleRepository.findByMdn(mdn)
			.orElseThrow(() -> CarException.sendError(ErrorCode.CAR_NOT_FOUND));
	}

	public CarEntity getCarDetail(String mdn) {
		return carModuleRepository.findDetailByMdn(mdn)
			.orElseThrow(() -> CarException.sendError(ErrorCode.CAR_NOT_FOUND));
	}

	public CarEntity updateCar(CarEntity car) {
		CarEntity updatedCar = carModuleRepository.save(car);

		GlobalSseEvent sseEvent = globalSseEvent.sendEvent(SseEvent.CAR_UPDATED);
		eventEmitterService.sendEvent("car_event", sseEvent);
		return updatedCar;
	}

	public CarEntity saveCar(CarEntity car) {
		CarEntity savedCar = carModuleRepository.save(car);

		GlobalSseEvent sseEvent = globalSseEvent.sendEvent(SseEvent.CAR_CREATED);
		eventEmitterService.sendEvent("car_event", sseEvent);
		return savedCar;
	}

	public void deleteCar(String mdn) {
		carModuleRepository.deleteByMdn(mdn);

		GlobalSseEvent sseEvent = globalSseEvent.sendEvent(SseEvent.CAR_DELETED);
		eventEmitterService.sendEvent("car_event", sseEvent);
	}
}
