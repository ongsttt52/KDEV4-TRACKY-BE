package kernel360.trackycore.core.domain.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.infrastructure.repository.DriveRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveProvider {

	private final DriveRepository driveRepository;

	public DriveEntity save(DriveEntity drive) {
		return driveRepository.save(drive);
	}

	public DriveEntity findById(Long id) {
		return driveRepository.findById(id)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.DRIVE_NOT_FOUND));
	}
}
