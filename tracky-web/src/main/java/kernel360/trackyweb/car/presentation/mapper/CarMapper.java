package kernel360.trackyweb.car.presentation.mapper;

import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackyweb.car.presentation.dto.CarCreateRequest;
import kernel360.trackyweb.car.presentation.dto.CarUpdateRequest;

public class CarMapper {

	public static CarEntity createCar(
		CarCreateRequest carCreateRequest,
		DeviceEntity device,
		BizEntity biz
	) {
		return CarEntity.create(
			carCreateRequest.mdn(),
			biz,
			device,
			carCreateRequest.carType(),
			carCreateRequest.carPlate(),
			carCreateRequest.carYear(),
			carCreateRequest.purpose(),
			carCreateRequest.status(),
			carCreateRequest.sum()
		);
	}

	public static void updateCar(CarEntity car, CarUpdateRequest carUpdateRequest, DeviceEntity device, BizEntity biz) {
		car.update(
			biz,
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
