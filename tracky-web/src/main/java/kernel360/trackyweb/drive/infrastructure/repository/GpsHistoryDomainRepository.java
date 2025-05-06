package kernel360.trackyweb.drive.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.infrastructure.repository.GpsHistoryRepository;

public interface GpsHistoryDomainRepository extends GpsHistoryRepository {

	@Query(value = """
		    SELECT * FROM gpshistory
		    WHERE drive_id = :driveId
		    ORDER BY drive_seq ASC
		    LIMIT 1
		""", nativeQuery = true)
	Optional<GpsHistoryEntity> findFirstGpsByDriveId(@Param("driveId") Long driveId);

	@Query(value = """
		    SELECT * FROM gpshistory
		    WHERE drive_id = :driveId
		    ORDER BY drive_seq DESC
		    LIMIT 1
		""", nativeQuery = true)
	Optional<GpsHistoryEntity> findLastGpsByDriveId(@Param("driveId") Long driveId);

	List<GpsHistoryEntity> findByDriveId(Long driveId);
}
