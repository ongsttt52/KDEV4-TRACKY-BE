package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.util.List;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;

public interface DashGpsHistoryRepositoryCustom {
	List<GpsHistoryEntity> getLatestGps(String bizUuid);
}
