package kernel360.trackyweb.drivehistory.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;

public interface GpsHistoryRepository extends JpaRepository<GpsHistoryEntity, Long> {

	Optional<GpsHistoryEntity> findFirstByDriveOrderByDriveSeqAsc(DriveEntity drive);

	Optional<GpsHistoryEntity> findFirstByDriveOrderByDriveSeqDesc(DriveEntity drive);

	List<GpsHistoryEntity> findByDrive(DriveEntity drive);
}
