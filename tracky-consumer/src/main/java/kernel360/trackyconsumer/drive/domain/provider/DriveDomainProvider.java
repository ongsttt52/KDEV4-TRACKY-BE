package kernel360.trackyconsumer.drive.domain.provider;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kernel360.trackyconsumer.drive.infrastructure.repository.DriveDomainRepository;
import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveDomainProvider {

	private final DriveDomainRepository driveDomainRepository;

	public DriveEntity getDrive(CarEntity car, LocalDateTime onTime) {
		return driveDomainRepository.getDrive(car, onTime)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.DRIVE_NOT_FOUND));
	}

	public DriveEntity save(DriveEntity drive) {
		return driveDomainRepository.save(drive);
	}
}
