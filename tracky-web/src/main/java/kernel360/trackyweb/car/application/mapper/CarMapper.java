package kernel360.trackyweb.car.application.mapper;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackyweb.car.presentation.dto.CarCreateRequest;
import kernel360.trackyweb.car.presentation.dto.CarUpdateRequest;

public class CarMapper {

	public static CarEntity createCar(
		CarCreateRequest carCreateRequest,
		DeviceEntity device
	) {
		return CarEntity.create(
			carCreateRequest.mdn(),
			carCreateRequest.bizId(),
			device,
			carCreateRequest.carType(),
			carCreateRequest.carPlate(),
			carCreateRequest.carYear(),
			carCreateRequest.purpose(),
			carCreateRequest.status(),
			carCreateRequest.sum()
		);
	}

	public static void updateCar(CarEntity car, CarUpdateRequest carUpdateRequest, DeviceEntity device) {
		car.update(
			carUpdateRequest.bizId(),
			device,
			carUpdateRequest.carType(),
			carUpdateRequest.carPlate(),
			carUpdateRequest.carYear(),
			carUpdateRequest.purpose(),
			carUpdateRequest.status(),
			carUpdateRequest.sum()
		);
	}
}
