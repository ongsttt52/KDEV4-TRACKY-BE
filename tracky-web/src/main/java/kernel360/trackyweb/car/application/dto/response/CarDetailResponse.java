package kernel360.trackyweb.car.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.common.entity.CarEntity;

public record CarDetailResponse(
	String mdn,
	Long bizId,
	String carType,
	String carPlate,
	String carYear,
	String purpose,
	String status,
	double sum,
	DeviceResponse deviceInfo,
	LocalDateTime createdAt
) {
	public static CarDetailResponse from(CarEntity car) {
		return new CarDetailResponse(
			car.getMdn(),
			car.getBiz().getId(),
			car.getCarType(),
			car.getCarPlate(),
			car.getCarYear(),
			car.getPurpose(),
			car.getStatus(),
			car.getSum(),
			DeviceResponse.from(car.getDevice()),
			car.getCreatedAt()
		);
	}
}
