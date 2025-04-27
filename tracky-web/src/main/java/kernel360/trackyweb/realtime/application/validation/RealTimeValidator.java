package kernel360.trackyweb.realtime.application.validation;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.DriveEntity;

@Component
public class RealTimeValidator {

	public void validateDriveIsRunning(DriveEntity drive) {
		if (drive.getDriveOffTime() != null) {
			throw new IllegalStateException("운행이 종료된 차량입니다.");
		}
	}
}
