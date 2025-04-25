package kernel360.trackyweb.drive.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public interface DriveDomainRepositoryCustom {

	Page<DriveEntity> searchByFilter(String mdn,
		LocalDateTime startDateTime,
		LocalDateTime endDateTime,
		Pageable pageable);

	Page<DriveEntity> findRunningDriveList(
		String search,
		Pageable pageable
	);

	Optional<DriveEntity> findRunningDriveById(Long driveId);
}
