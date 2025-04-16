package kernel360.trackyweb.car.domain.provider;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.provider.CarProvider;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackyweb.car.application.dto.request.CarSearchByFilterRequest;
import kernel360.trackyweb.car.infrastructure.repository.CarDomainRepository;
import kernel360.trackyweb.car.infrastructure.repository.CarRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarDomainProvider {

	private final CarRepositoryCustom carRepositoryCustom;
	private final CarDomainRepository carDomainRepository;
	private final CarProvider carProvider;

	public CarEntity getCar(String mdn) {
		return carProvider.findByMdn(mdn)
			.orElseThrow(() -> CarException.sendError(ErrorCode.CAR_NOT_FOUND));
	}

	public Page<CarEntity> searchByFilter(CarSearchByFilterRequest carSearchByFilterRequest) {
		return carRepositoryCustom.searchByFilter(carSearchByFilterRequest.mdn(), carSearchByFilterRequest.status(),
			carSearchByFilterRequest.purpose(), carSearchByFilterRequest.pageable());
	}

	public CarEntity save(CarEntity car) {
		return carProvider.saveCar(car);
	}

	public CarEntity getCarDetail(String mdn) {
		return carDomainRepository.findDetailByMdn(mdn)
			.orElseThrow(() -> CarException.sendError(ErrorCode.CAR_NOT_FOUND));
	}

	public void existsByMdn(String mdn) {
		if (carProvider.existsByMdn(mdn)) {
			throw CarException.sendError(ErrorCode.CAR_DUPLICATED);
		}
	}

	public boolean isMdnExist(String mdn) {
		return carProvider.existsByMdn(mdn);
	}

	public void delete(String mdn) {
		carDomainRepository.deleteByMdn(mdn);
	}
}
