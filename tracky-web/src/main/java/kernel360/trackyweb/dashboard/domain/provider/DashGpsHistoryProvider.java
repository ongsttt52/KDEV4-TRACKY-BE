package kernel360.trackyweb.dashboard.domain.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.querydsl.core.Tuple;

import kernel360.trackyweb.dashboard.domain.projection.GpsLatLonProjection;
import kernel360.trackyweb.dashboard.infrastructure.repository.DashGpsHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DashGpsHistoryProvider {

	private final DashGpsHistoryRepository dashGpsHistoryRepository;

	public Map<String, int[]> findLatestGps(Long bizId) {

		StopWatch st = new StopWatch("findLatestGps");
		st.start();
		List<Tuple> response = dashGpsHistoryRepository.getLatestGps(bizId);
		st.stop();

		log.info("{}", st.prettyPrint());

		int[] lats = new int[response.size()];
		int[] lons = new int[response.size()];

		for (int i = 0; i < response.size(); i++) {
			lats[i] = response.get(i).get(0, Integer.class);
			lons[i] = response.get(i).get(1, Integer.class);
		}

		return new HashMap<>() {{
			put("lats", lats);
			put("lons", lons);
		}};
	}

	public Map<String, int[]> findLatestLatLon(Long bizId) {

		StopWatch st = new StopWatch("findLatestByNativeQuery");
		st.start();
		List<GpsLatLonProjection> latLons = dashGpsHistoryRepository.findLatestLatLonByBizId(bizId);
		st.stop();

		log.info("{}", st.prettyPrint());

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
