package kernel360.trackyweb.dashboard.domain.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackyweb.dashboard.domain.projection.GpsLatLonProjection;
import kernel360.trackyweb.dashboard.infrastructure.repository.DashGpsHistoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DashGpsHistoryProvider {

	private final DashGpsHistoryRepository dashGpsHistoryRepository;

	public List<GpsHistoryEntity> findLatestGps(String bizUuid) {
		return dashGpsHistoryRepository.getLatestGps(bizUuid);
	}

	public Map<String, int[]> findLatestLatLon(Long bizId) {

		StopWatch st = new StopWatch("findLatestByNativeQuery");
		st.start();
		List<GpsLatLonProjection> latLons = dashGpsHistoryRepository.findLatestLatLonByBizId(bizId);
		st.stop();

		System.out.println(st.prettyPrint());

		int[] lats = new int[latLons.size()];
		int[] lons = new int[latLons.size()];

		for (int i = 0; i < latLons.size(); i++) {
			GpsLatLonProjection latLon = latLons.get(i);
			lats[i] = latLon.getLat().intValue();
			lons[i] = latLon.getLon().intValue();
		}

		return new HashMap<>() {{
			put("lats", lats);
			put("lons", lons);
		}};
	}
}
