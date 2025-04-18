package kernel360.trackyweb.car.application.dto.request;

import kernel360.trackycore.core.domain.entity.CarEntity;

public record CarCreateRequest(
	String mdn,
	Long bizId,
	Long device,
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
			car.getBiz().getId(),
			car.getDevice().getId(),
			car.getCarType(),
			car.getCarPlate(),
			car.getCarYear(),
			car.getPurpose(),
			car.getStatus(),
			car.getSum()
		);
	}
}
