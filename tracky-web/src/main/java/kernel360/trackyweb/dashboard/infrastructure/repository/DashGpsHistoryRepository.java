package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryId;

@Repository
public interface DashGpsHistoryRepository extends JpaRepository<GpsHistoryEntity, GpsHistoryId> {

	@Query(value = """
		SELECT g.*
		FROM gpshistory g
		JOIN drive d ON g.drive_id = d.id
		WHERE (d.mdn, g.created_at) IN (
		  SELECT d2.mdn, MAX(g2.created_at)
		  FROM gpshistory g2
		  JOIN drive d2 ON g2.drive_id = d2.id
		  GROUP BY d2.mdn
		)
		""", nativeQuery = true)
	List<GpsHistoryEntity> findLatestGpsByMdn();

}
