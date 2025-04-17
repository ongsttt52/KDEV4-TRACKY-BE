package kernel360.trackyconsumer.domain.provider;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kernel360.trackyconsumer.infrastructure.repository.DriveDomainRepository;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.common.provider.DriveProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveDomainProvider {

	private final DriveDomainRepository driveDomainRepository;
	private final DriveProvider driveProvider;

	public DriveEntity findByCarAndOtime(CarEntity car, LocalDateTime otime) {
		return driveDomainRepository.findByCarAndOtime(car, otime)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.DRIVE_NOT_FOUND));
	}

	public DriveEntity save(DriveEntity drive) {
		return driveProvider.save(drive);
	}
}
