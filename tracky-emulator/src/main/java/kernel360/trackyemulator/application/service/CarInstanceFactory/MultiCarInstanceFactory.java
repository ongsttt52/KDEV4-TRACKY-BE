package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;
import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MultiCarInstanceFactory {

	private final RandomLocationGenerator locationGenerator;
	private final Set<String> usedMdns = new HashSet<>();
	private static final Random random = new Random();

	public List<EmulatorInstance> createCarInstances(int count, List<String> mdnList) {
		if (mdnList == null || mdnList.isEmpty()) {
			throw new IllegalStateException("MDN 리스트가 비어있습니다. 먼저 초기화하세요.");
		}

		int availableCount = mdnList.size() - usedMdns.size();
		if (count > availableCount) {
			throw new IllegalArgumentException("요청한 개수보다 사용 가능한 MDN이 부족합니다. (남은 수: " + availableCount + ")");
		}

		List<EmulatorInstance> instances = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			String mdn = pickUniqueMdn(mdnList);

			int lat = locationGenerator.randomLatitude();
			int lon = locationGenerator.randomLongitude();
			int ang = locationGenerator.randomAngle();

			EmulatorInfo emulatorInfo = EmulatorInfo.create();
			GpsInfo gpsInfo = GpsInfo.create(lat, lon, ang, 0, 0.0);
			EmulatorInstance car = EmulatorInstance.create(mdn, emulatorInfo, gpsInfo, LocalDateTime.now());

			instances.add(car);
		}

		return instances;
	}

	private String pickUniqueMdn(List<String> mdnList) {
		while (true) {
			int index = random.nextInt(mdnList.size());
			String mdn = mdnList.get(index);

			if (!usedMdns.contains(mdn)) {
				usedMdns.add(mdn);
				return mdn;
			}
		}
	}

	public void resetUsedMdns() {
		usedMdns.clear();
	}
}
