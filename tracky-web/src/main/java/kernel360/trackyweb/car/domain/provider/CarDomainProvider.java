package kernel360.trackyweb.car.domain.provider;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackyweb.car.application.dto.request.CarSearchByFilterRequest;
import kernel360.trackyweb.car.infrastructure.repository.CarDomainRepository;
import kernel360.trackyweb.car.infrastructure.repository.CarRepositoryCustom;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarDomainProvider {

	private final CarRepositoryCustom carRepositoryCustom;
	private final CarDomainRepository carDomainRepository;

	public Page<CarEntity> searchByFilter(CarSearchByFilterRequest carSearchByFilterRequest) {
		return carRepositoryCustom.searchByFilter(carSearchByFilterRequest.mdn(), carSearchByFilterRequest.status(),
			carSearchByFilterRequest.purpose(), carSearchByFilterRequest.pageable());
	}

	public CarEntity save(CarEntity car) {
		return carDomainRepository.save(car);
	}

	public void delete(String mdn) {
		carDomainRepository.deleteByMdn(mdn);
	}

	public List<CarListResponse> getCar() {
		return carDomainRepository.findAllCars();
	}
}
