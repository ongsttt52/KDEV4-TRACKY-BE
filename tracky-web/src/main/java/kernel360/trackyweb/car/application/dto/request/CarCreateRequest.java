package kernel360.trackyweb.car.application.dto.request;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarCreateRequest(
	String mdn,
	Long bizId,
	Long device,
	CarType carType,
	String carName,
	String carPlate,
	String carYear,
	String purpose,
	CarStatus status,
	double sum
) {
	public static CarCreateRequest from(CarEntity car) {
		return new CarCreateRequest(
			car.getMdn(),
			car.getBiz().getId(),
			car.getDevice().getId(),
			car.getCarType(),
			car.getCarName(),
			car.getCarPlate(),
			car.getCarYear(),
			car.getPurpose(),
			car.getStatus(),
			car.getSum()
		);
	}
}
