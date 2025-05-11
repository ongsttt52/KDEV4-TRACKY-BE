package kernel360.trackyweb.admin.search.application.response;

import java.util.List;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record AdminCarResponse(
	String mdn,
	CarType carType,
	String carName,
	String carPlate,
	String carYear,
	String purpose,
	CarStatus status,
	String bizName
) {
	public static AdminCarResponse from(CarEntity car) {
		return new AdminCarResponse(
			car.getMdn(),
			car.getCarType(),
			car.getCarName(),
			car.getCarPlate(),
			car.getCarYear(),
			car.getPurpose(),
			car.getStatus(),
			car.getBiz().getBizName()
		);

	}

	public static List<AdminCarResponse> fromList(List<CarEntity> cars) {
		return cars.stream()
			.map(AdminCarResponse::from)
			.toList();
	}
}
