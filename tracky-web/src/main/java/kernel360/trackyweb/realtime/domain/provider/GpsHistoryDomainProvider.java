package kernel360.trackyweb.realtime.domain.provider;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackyweb.drive.infrastructure.repository.GpsHistoryDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GpsHistoryDomainProvider {

	private final GpsHistoryDomainRepository gpsHistoryDomainRepository;

	@Transactional(readOnly = true)
	public List<GpsHistoryEntity> getGpsPathBeforeTime(Long driveId, LocalDateTime nowTime) {
		return gpsHistoryDomainRepository.findGpsPathBeforeTime(driveId, nowTime);
	}

}
