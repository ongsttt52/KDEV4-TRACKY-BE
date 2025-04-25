package kernel360.trackyweb.realtime.domain.provider;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackyweb.drive.infrastructure.repository.GpsHistoryDomainRepository;
import kernel360.trackyweb.realtime.application.dto.response.GpsDataResponse;
import kernel360.trackyweb.realtime.infrastructure.repository.GpsHistoryRepositoryCustom;

@Component
//@RequiredArgsConstructor
public class GpsHistoryDomainProvider {

	private final GpsHistoryDomainRepository gpsHistoryDomainRepository;
	private final GpsHistoryRepositoryCustom gpsHistoryRepositoryCustom;

	public GpsHistoryDomainProvider(
		GpsHistoryDomainRepository gpsHistoryDomainRepository,
		@Qualifier("gpsHistoryRepositoryCustomImpl") GpsHistoryRepositoryCustom gpsHistoryRepositoryCustom
	) {
		this.gpsHistoryDomainRepository = gpsHistoryDomainRepository;
		this.gpsHistoryRepositoryCustom = gpsHistoryRepositoryCustom;
	}

	@Transactional(readOnly = true)
	public GpsDataResponse getOneGpsByDriveId(Long id) {
		return gpsHistoryDomainRepository.findOneGpsByDriveId(id)
			.map(GpsDataResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("해당 driveId에 GPS 데이터가 없습니다."));
	}

	public List<GpsHistoryEntity> getGpsListAfterTime(Long driveId, LocalDateTime nowTime) {
		return gpsHistoryRepositoryCustom.findGpsListAfterTime(driveId, nowTime);
	}

}
