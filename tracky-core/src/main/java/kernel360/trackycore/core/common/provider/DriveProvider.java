package kernel360.trackycore.core.common.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.infrastructure.repository.DriveRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveProvider {

	private final DriveRepository driveRepository;

	public DriveEntity save(DriveEntity drive) {
		return driveRepository.save(drive);
	}
}
