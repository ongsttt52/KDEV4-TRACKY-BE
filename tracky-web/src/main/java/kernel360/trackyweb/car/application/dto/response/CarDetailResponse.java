package kernel360.trackyweb.car.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarDetailResponse(
	String mdn,
	BizResponse bizInfo,
	CarType carType,
	String carPlate,
	String carYear,
	String purpose,
	CarStatus status,
	double sum,
	DeviceResponse deviceInfo,
	LocalDateTime createdAt
) {
	public static CarDetailResponse from(CarEntity car) {
		return new CarDetailResponse(
			car.getMdn(),
			BizResponse.from(car.getBiz()),
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
