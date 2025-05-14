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
		)
		SELECT
		  d.mdn    AS mdn,
		  gh.drive_seq AS driveSeq,
		  gh.lat    AS lat,
		  gh.lon    AS lon
		FROM latest_seq ls
		JOIN gpshistory gh
		  ON gh.drive_id = ls.drive_id
		 AND gh.drive_seq = ls.max_seq
		JOIN drive d
		  ON d.id = ls.drive_id
		JOIN car c
		  ON c.mdn = d.mdn
		 AND c.biz_id = :bizId
		GROUP BY c.mdn
		ORDER BY gh.drive_seq DESC
		""",
		nativeQuery = true
	)
	List<GpsLatLonProjection> findLatestLatLonByBizId(@Param("bizId") Long bizId);
}
