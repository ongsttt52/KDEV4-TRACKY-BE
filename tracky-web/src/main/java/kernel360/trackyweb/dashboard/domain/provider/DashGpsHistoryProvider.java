package kernel360.trackyweb.dashboard.domain.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackyweb.dashboard.infrastructure.repository.DashGpsHistoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DashGpsHistoryProvider {

	private final DashGpsHistoryRepository dashGpsHistoryRepository;

	public List<GpsHistoryEntity> findLatestGps(String bizUuid) {
		return dashGpsHistoryRepository.getLatestGps(bizUuid);
	}
}
