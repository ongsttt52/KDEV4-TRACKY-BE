package kernel360.trackyweb.car.domain.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackycore.core.infrastructure.exception.DeviceException;
import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackyweb.car.infrastructure.repo.CarDeviceRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeviceProvider {

	private final CarDeviceRepository carDeviceRepository;

	public DeviceEntity getDevice(Long id) {
		DeviceEntity device = carDeviceRepository.findById(id)
			.orElseThrow(() -> DeviceException.sendError(ErrorCode.DEVICE_NOT_FOUND));
		return device;
	}

}
