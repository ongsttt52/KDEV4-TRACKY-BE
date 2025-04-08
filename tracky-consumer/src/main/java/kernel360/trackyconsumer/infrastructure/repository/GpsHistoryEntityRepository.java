package kernel360.trackyconsumer.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryId;

@Repository
public interface GpsHistoryEntityRepository extends JpaRepository<GpsHistoryEntity, GpsHistoryId> {

	@Query("SELECT COALESCE(MAX(gh.driveSeq), 0L) FROM GpsHistoryEntity gh WHERE gh.drive = :drive")
	long findMaxSeqByDrive(DriveEntity drive);
}
