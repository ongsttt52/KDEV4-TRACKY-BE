package kernel360.trackyweb.car.application.dto.response;

import java.util.List;

import kernel360.trackycore.core.domain.entity.CarEntity;

public record CarResponse(
	String mdn,
	String carType,
	String carName,
	String carPlate,
	String carYear,
	String purpose,
	String status
) {
	public static CarResponse from(CarEntity car) {
		return new CarResponse(
			car.getMdn(),
			car.getCarType(),
			car.getCarName(),
			car.getCarPlate(),
			car.getCarYear(),
			car.getPurpose(),
			car.getStatus()
		);

	}

	public static List<CarResponse> fromList(List<CarEntity> cars) {
		return cars.stream()
			.map(CarResponse::from)
			.toList();
	}
}

