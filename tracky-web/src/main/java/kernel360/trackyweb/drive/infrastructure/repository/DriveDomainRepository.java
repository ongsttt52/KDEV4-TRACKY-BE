package kernel360.trackyweb.drive.infrastructure.repository;

import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.infrastructure.repository.DriveRepository;

public interface DriveDomainRepository extends DriveRepository, DriveDomainRepositoryCustom {
	@Query("SELECT SUM(d.driveDistance) FROM DriveEntity d")
	Double getTotalDriveDistance();

	@Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, d.driveOnTime, d.driveOffTime)) FROM DriveEntity d WHERE d.driveOnTime IS NOT NULL AND d.driveOffTime IS NOT NULL")
	Long getTotalDriveDurationInMinutes();
}
