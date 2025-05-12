package kernel360.trackyweb.drive.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.application.dto.internal.NonOperatedCar;
import kernel360.trackyweb.drive.application.dto.internal.OperationCarCount;
import kernel360.trackyweb.drive.application.dto.internal.OperationTotalCount;
import kernel360.trackyweb.drive.domain.vo.DriveHistory;

public interface DriveDomainRepositoryCustom {

	Page<DriveEntity> searchByFilter(
		String search,
		String mdn,
		LocalDate startDateTime,
		LocalDate endDateTime,
		Pageable pageable);

	Page<DriveEntity> findRunningDriveList(
		String search,
		Pageable pageable
	);

	Optional<DriveEntity> findRunningDriveById(Long driveId);

	List<DriveEntity> findDriveListByMdn(String mdn);

	Optional<DriveHistory> findByDriveId(Long driveId);

	Optional<Long> findRunningDriveIdByMdn(String mdn);

	List<OperationCarCount> getDailyOperationCar(LocalDate targetDate);

	List<OperationTotalCount> getDailyTotalOperation(LocalDate targetDate);

	List<NonOperatedCar> getNonOperatedCars(LocalDate targetDate);
}
