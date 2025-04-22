package kernel360.trackyweb.dashboard.infrastructure.repository.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public interface DashDriveRepository extends JpaRepository<DriveEntity, Long> {
	@Query("SELECT SUM(d.driveDistance) FROM DriveEntity d")
	Double getTotalDriveDistance();

	@Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, d.driveOnTime, d.driveOffTime)) FROM DriveEntity d WHERE d.driveOnTime IS NOT NULL AND d.driveOffTime IS NOT NULL")
	Long getTotalDriveDurationInMinutes();
}
