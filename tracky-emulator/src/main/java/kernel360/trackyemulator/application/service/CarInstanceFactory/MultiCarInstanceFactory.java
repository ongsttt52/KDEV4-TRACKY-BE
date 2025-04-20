package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackycore.core.domain.vo.GpsInfo;
import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.exception.EmulatorException;
import kernel360.trackyemulator.infrastructure.exception.ErrorCode;

@Component
public class MultiCarInstanceFactory {

	private final Set<String> usedMdns = new HashSet<>();
	private static final Random random = new Random();

	public List<EmulatorInstance> createCarInstances(int count, List<String> mdnList) {
		if (mdnList == null || mdnList.isEmpty()) {
			throw EmulatorException.sendError(ErrorCode.MDN_NOT_FOUND);
		}

		if (count > availableMdnCount(mdnList)) {
			throw EmulatorException.sendError(ErrorCode.MDN_INSUFFICIENT);
		}

		List<EmulatorInstance> instances = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			String mdn = pickUniqueMdn(mdnList);

			int lat = RandomLocationGenerator.randomLatitude();
			int lon = RandomLocationGenerator.randomLongitude();
			int ang = RandomLocationGenerator.randomAngle();

			EmulatorInfo emulatorInfo = EmulatorInfo.create();
			GpsInfo gpsInfo = GpsInfo.create(lat, lon, ang, 0, 0.0);
			EmulatorInstance car = EmulatorInstance.create(mdn, emulatorInfo, gpsInfo, LocalDateTime.now());

			instances.add(car);
		}

		return instances;
	}

	//세션 초기화 시 인스턴스 리스트 초기화
	public void resetUsedMdns() {
		usedMdns.clear();
	}

	//MDN 리스트 중 랜덤으로 한개의 MDN 선택해오는 메소드
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

	//현재 사용 가능한 MDN 리스트 size
	private int availableMdnCount(List<String> mdnList) {
		return mdnList.size() - usedMdns.size();
	}
}
