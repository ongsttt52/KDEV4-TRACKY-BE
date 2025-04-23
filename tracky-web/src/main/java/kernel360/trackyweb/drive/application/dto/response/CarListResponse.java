package kernel360.trackyweb.drive.application.dto.response;

import kernel360.trackycore.core.domain.entity.CarEntity;

public record CarListResponse(
	String mdn,
	String carPlate,
	String carType,
	String status
) {
	public static CarListResponse from(CarEntity car) {
		return new CarListResponse(
			car.getMdn(),
			car.getCarPlate(),
			car.getCarType(),
			car.getStatus()
		);
	}
}
