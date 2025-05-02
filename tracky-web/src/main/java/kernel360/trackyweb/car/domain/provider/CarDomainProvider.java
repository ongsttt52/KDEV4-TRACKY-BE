package kernel360.trackyweb.car.domain.provider;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;
import kernel360.trackyweb.car.application.dto.internal.CarCountWithBizId;
import kernel360.trackyweb.car.infrastructure.repository.CarDomainRepository;
import kernel360.trackyweb.common.sse.GlobalSseEvent;
import kernel360.trackyweb.common.sse.SseEvent;
import kernel360.trackyweb.dashboard.domain.CarStatusTemp;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarDomainProvider {

	private final CarDomainRepository carDomainRepository;
	private final GlobalSseEvent globalSseEvent;

	public Page<CarEntity> searchCarByFilter(
		String bizUuid,
		String search,
		CarStatus status,
		CarType carType,
		Pageable pageable
	) {
		return carDomainRepository.searchCarByFilter(bizUuid, search, status, carType, pageable);
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

	public List<CarEntity> getAllByBizUuid(String bizUuid) {
		return carDomainRepository.findAllByBizUuid(bizUuid);
	}

	public List<CarStatusTemp> getAllGroupedByStatus() {
		return carDomainRepository.findAllGroupedByStatus();
	}

	public List<CarCountWithBizId> coundAllCarGroupedByBizId() {
		return carDomainRepository.findTotalMdnsGroupedByBizId();
	}
}
