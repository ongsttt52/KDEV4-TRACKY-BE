package kernel360.trackyweb.admin.search.application.response;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;

public record AdminCarListResponse (
	String mdn,
	String bizName,
	String carPlate,
	String carName,
	CarType carType,
	String car_year,
	CarStatus status
) {
	public static AdminCarListResponse from(CarEntity car) {
		return new AdminCarListResponse(
			car.getMdn(),
			car.getBiz().getBizName(),
			car.getCarPlate(),
			car.getCarName(),
			car.getCarType(),
			car.getCarYear(),
			car.getStatus()
		);
	}
}
