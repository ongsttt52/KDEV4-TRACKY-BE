package kernel360.trackyweb.car.application.dto.response;

import kernel360.trackycore.core.domain.entity.DeviceEntity;

public record DeviceResponse(
	Long id,
	String mid,
	String did,
	String pv
) {
	public static DeviceResponse from(DeviceEntity device) {
		return new DeviceResponse(
			device.getId(),
			device.getMid(),
			device.getDid(),
			device.getPv()
		);
	}
}
