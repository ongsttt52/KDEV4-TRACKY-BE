package kernel360.trackyweb.car.application.dto.request;

import kernel360.trackycore.core.domain.entity.DeviceEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarUpdateRequest(
	String mdn,
	DeviceEntity device,
	CarType carType,
	String carName,
	String carPlate,
	String carYear,
	String purpose,
	CarStatus status,
	double sum
) {
}
