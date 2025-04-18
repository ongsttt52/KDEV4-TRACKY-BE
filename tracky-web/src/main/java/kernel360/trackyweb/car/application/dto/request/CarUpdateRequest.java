package kernel360.trackyweb.car.application.dto.request;

import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackycore.core.domain.entity.DeviceEntity;

public record CarUpdateRequest(
	BizEntity biz,
	DeviceEntity device,
	String carType,
	String carPlate,
	String carYear,
	String purpose,
	String status,
	double sum
) {
}
