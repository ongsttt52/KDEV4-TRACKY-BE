package kernel360.trackyweb.car.presentation.dto;

import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;

public record CarCreateRequest(
	String mdn,
	BizEntity bizId,
	DeviceEntity device,
	String carType,
	String carPlate,
	String carYear,
	String purpose,
	String status,
	double sum
) {
	public static CarCreateRequest from(CarEntity car) {
		return new CarCreateRequest(
			car.getMdn(),
			car.getBiz(),
			car.getDevice(),
			car.getCarType(),
			car.getCarPlate(),
			car.getCarYear(),
			car.getPurpose(),
			car.getStatus(),
			car.getSum()
		);
	}
}
