package kernel360.trackyweb.car.application.mapper;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackyweb.car.presentation.dto.CarRequest;

public class CarMapper {

	public static CarEntity createCar(
		CarRequest carRequest
	) {
		return CarEntity.create(
			carRequest.mdn(),
			carRequest.bizId(),
			carRequest.device(),
			carRequest.carType(),
			carRequest.carPlate(),
			carRequest.carYear(),
			carRequest.purpose(),
			carRequest.status(),
			carRequest.sum()
		);
	}




}
