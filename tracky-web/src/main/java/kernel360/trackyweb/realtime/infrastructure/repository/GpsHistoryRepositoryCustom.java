package kernel360.trackyweb.realtime.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;

public interface GpsHistoryRepositoryCustom {
	Optional<GpsHistoryEntity> findOneGpsByDriveId(Long id);

	List<GpsHistoryEntity> findGpsListAfterTime(Long driveId, LocalDateTime afterTime);

}
