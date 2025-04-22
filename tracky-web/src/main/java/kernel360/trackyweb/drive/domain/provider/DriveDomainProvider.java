package kernel360.trackyweb.drive.domain.provider;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.infrastructure.repository.DriveDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveDomainProvider {

	private final DriveDomainRepository driveDomainRepository;

	public Page<DriveEntity> searchDrivesByFilter(
		String mdn,
		LocalDateTime startDateTime,
		LocalDateTime endDateTime,
		Pageable pageable) {
		return driveDomainRepository.searchByFilter(mdn, startDateTime, endDateTime, pageable);
	}

	public Page<DriveEntity> findRunningDriveList(
		String search,
		Pageable pageable
	) {
		return driveDomainRepository.findRunningDriveList(search, pageable);
	}

}
