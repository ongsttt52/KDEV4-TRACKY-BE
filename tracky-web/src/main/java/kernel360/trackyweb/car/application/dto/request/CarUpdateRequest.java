package kernel360.trackyweb.car.application.dto.request;

import kernel360.trackycore.core.domain.entity.DeviceEntity;

public record CarUpdateRequest(
	String mdn,
	DeviceEntity device,
	String carType,
	String carName,
	String carPlate,
	String carYear,
	String purpose,
	String status,
	double sum
) {
}
