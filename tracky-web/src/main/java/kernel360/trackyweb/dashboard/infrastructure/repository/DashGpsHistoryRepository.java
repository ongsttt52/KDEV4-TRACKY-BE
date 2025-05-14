package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.repository.GpsHistoryRepository;
import kernel360.trackyweb.dashboard.domain.projection.GpsLatLonProjection;

@Repository
public interface DashGpsHistoryRepository extends GpsHistoryRepository, DashGpsHistoryRepositoryCustom {

	@Query(
		value = """
			SELECT
			  gh.lat, gh.lon
			FROM gpshistory gh
			JOIN (
			  SELECT
			    d2.id   AS drive_id,
			    MAX(gh2.drive_seq) AS max_seq
			  FROM drive d2
			  JOIN car   c2   ON d2.mdn = c2.mdn
			                AND c2.biz_id = :bizId
			  JOIN gpshistory gh2
			    ON gh2.drive_id = d2.id
			  GROUP BY d2.id
			) AS latest
			  ON gh.drive_id = latest.drive_id
			 AND gh.drive_seq = latest.max_seq
			ORDER BY gh.drive_seq DESC
			""",
		nativeQuery = true
	)
	List<GpsLatLonProjection> findLatestLatLonByBizId(@Param("bizId") Long bizId);
}
