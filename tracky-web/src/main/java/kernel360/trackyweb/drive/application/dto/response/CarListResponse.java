package kernel360.trackyweb.drive.application.dto.response;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarListResponse(
	String mdn,
	String carPlate,
	String carName,
	CarType carType,
	String car_year,
	CarStatus status
) {
	public static CarListResponse from(CarEntity car) {
		return new CarListResponse(
			car.getMdn(),
			car.getCarPlate(),
			car.getCarName(),
			car.getCarType(),
			car.getCarYear(),
			car.getStatus()
		);
	}
}
