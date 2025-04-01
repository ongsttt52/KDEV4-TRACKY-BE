package kernel360.trackyweb.car.application.mapper;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackyweb.car.presentation.dto.CarRequest;

public class CarMapper {

	public static CarEntity createCar(
		CarRequest carRequest,
		DeviceEntity device
	) {
		return CarEntity.create(
			carRequest.mdn(),
			carRequest.bizId(),
			device,
			carRequest.carType(),
			carRequest.carPlate(),
			carRequest.carYear(),
			carRequest.purpose(),
			carRequest.status(),
			carRequest.sum()
		);
	}

	public static void updateCar(CarEntity car, CarRequest carRequest, DeviceEntity device) {
		car.update(
			carRequest.mdn(),
			carRequest.bizId(),
			device,
			carRequest.carType(),
			carRequest.carPlate(),
			carRequest.carYear(),
			carRequest.purpose(),
			carRequest.status(),
			carRequest.sum()
		);
	}
}
