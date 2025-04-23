package kernel360.trackyweb.car.domain.provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackyweb.car.infrastructure.repository.CarDomainRepository;
import kernel360.trackyweb.common.sse.GlobalSseEvent;
import kernel360.trackyweb.common.sse.SseEvent;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarDomainProvider {

	private final CarDomainRepository carDomainRepository;
	private final GlobalSseEvent globalSseEvent;

	public Page<CarEntity> searchCarByFilter(String search, String status, String carType, Pageable pageable) {
		return carDomainRepository.searchCarByFilter(search, status, carType, pageable);
	}

	public CarEntity update(CarEntity car) {

		globalSseEvent.sendEvent(SseEvent.CAR_UPDATED);

		return carDomainRepository.save(car);
	}

	public CarEntity save(CarEntity car) {

		globalSseEvent.sendEvent(SseEvent.CAR_CREATED);

		return carDomainRepository.save(car);
	}

	public void delete(String mdn) {
		globalSseEvent.sendEvent(SseEvent.CAR_DELETED);

		carDomainRepository.deleteByMdn(mdn);
	}

	public Page<CarEntity> searchDriveCarByFilter(String bizUuid, String search, Pageable pageable) {
		return carDomainRepository.searchDriveCarByFilter(bizUuid, search, pageable);
	}
}
