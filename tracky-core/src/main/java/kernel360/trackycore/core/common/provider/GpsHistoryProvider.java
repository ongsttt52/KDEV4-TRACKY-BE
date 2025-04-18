package kernel360.trackycore.core.common.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.infrastructure.repository.GpsHistoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GpsHistoryProvider {

	private final GpsHistoryRepository gpsHistoryRepository;

	public GpsHistoryEntity save(GpsHistoryEntity gpsHistory) {
		return gpsHistoryRepository.save(gpsHistory);
	}
}
