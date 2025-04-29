package kernel360.trackyweb.drive.domain.provider;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.domain.DriveHistory;
import kernel360.trackyweb.drive.infrastructure.repository.DriveDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriveDomainProvider {

	private final DriveDomainRepository driveDomainRepository;

	public Page<DriveEntity> searchDrivesByFilter(
		String search,
		String mdn,
		LocalDate startDateTime,
		LocalDate endDateTime,
		Pageable pageable) {
		return driveDomainRepository.searchByFilter(search, mdn, startDateTime, endDateTime, pageable);
	}

	public Page<DriveEntity> findRunningDriveList(
		String search,
		Pageable pageable
	) {
		return driveDomainRepository.findRunningDriveList(search, pageable);
	}

	public DriveEntity findRunningDriveById(Long driveId) {
		return driveDomainRepository.findRunningDriveById(driveId)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.NOT_REALTIME_DRIVE));
	}

	public  DriveHistory findByDriveId(Long driveId) {
		return driveDomainRepository.findByDriveId(driveId)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.DRIVE_NOT_FOUND));
	}

	public Double getTotalDriveDistance() {
		return driveDomainRepository.getTotalDriveDistance();
	}

	public Long getTotalDriveDurationInMinutes() {
		return driveDomainRepository.getTotalDriveDurationInMinutes();
	}

}
