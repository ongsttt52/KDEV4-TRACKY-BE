package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.repository.GpsHistoryRepository;
import kernel360.trackyweb.dashboard.domain.projection.GpsLatLonProjection;

@Repository
public interface DashGpsHistoryRepository extends GpsHistoryRepository, DashGpsHistoryRepositoryCustom {

	@Query(value = """
		WITH latest_seq AS (
		  SELECT
			gh2.drive_id,
			MAX(gh2.drive_seq) AS max_seq
		  FROM gpshistory gh2
		  GROUP BY gh2.drive_id
		), latest_drive AS (
			SELECT mdn, MAX(d3.id) AS max_drive
			FROM drive d3
			GROUP BY d3.mdn
		)
		SELECT
		gh.lat, gh.lon
		FROM latest_seq ls
		JOIN latest_drive ld
			ON ld.max_drive = ls.drive_id
		JOIN gpshistory gh
			ON gh.drive_id = ld.max_drive
			AND gh.drive_seq = ls.max_seq
		JOIN car c
		  ON c.mdn = ld.mdn
		 AND c.biz_id = :bizId
		 GROUP BY c.mdn
		""",
		nativeQuery = true
	)
	List<GpsLatLonProjection> findLatestLatLonByBizId(@Param("bizId") Long bizId);
}
