package kernel360.trackyweb.car.domain.provider;

import java.util.List;
import java.util.Map;

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
import kernel360.trackyweb.statistic.application.dto.response.CarStatisticResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarDomainProvider {

	private final CarDomainRepository carDomainRepository;
	private final GlobalSseEvent globalSseEvent;

	public Page<CarEntity> searchCarByFilterAdmin(
		String bizSearch,
		String search,
		CarStatus status,
		CarType carType,
		Pageable pageable
	) {
		return carDomainRepository.searchCarByFilterAdmin(
			bizSearch, search, status, carType, pageable
		);
	}

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
		return carDomainRepository.save(car);
	}

	public CarEntity save(CarEntity car) {
		return carDomainRepository.save(car);
	}

	public void delete(String mdn) {
		carDomainRepository.deleteByMdn(mdn);
	}

	public Page<CarEntity> searchDriveCarByFilterAdmin(String bizSearch, String search, Pageable pageable) {
		return carDomainRepository.searchDriveCarByFilterAdmin(bizSearch, search, pageable);
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

	public Map<Long, Integer> countDailyTotalCar() {
		return CarCountWithBizId.toMap(carDomainRepository.getDailyTotalCarCount());
	}

	public List<CarEntity> findAllByAvailableEmulate(String bizUuid) {
		return carDomainRepository.availableEmulate(bizUuid);
	}

	public Page<CarStatisticResponse> searchCarStatisticByFilter(Long bizId, String search, Pageable pageable) {
		return carDomainRepository.searchCarStatisticByFilter(bizId, search, pageable);
	}
}
