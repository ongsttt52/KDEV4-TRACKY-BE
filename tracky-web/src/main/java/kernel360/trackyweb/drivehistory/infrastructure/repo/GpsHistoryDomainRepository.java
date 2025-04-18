package kernel360.trackyweb.drivehistory.infrastructure.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kernel360.trackycore.core.common.entity.GpsHistoryEntity;

public interface GpsHistoryDomainRepository extends JpaRepository<GpsHistoryEntity, Long> {

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
