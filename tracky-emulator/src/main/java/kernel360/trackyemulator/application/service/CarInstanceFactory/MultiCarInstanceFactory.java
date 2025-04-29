package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackycore.core.domain.vo.GpsInfo;
import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.exception.EmulatorException;
import kernel360.trackyemulator.infrastructure.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MultiCarInstanceFactory {

	List<EmulatorInstance> instances = new ArrayList<>();

	public List<EmulatorInstance> createCarInstances(List<String> mdnList) {

		if (mdnList == null || mdnList.isEmpty()) {
			throw EmulatorException.sendError(ErrorCode.MDN_NOT_FOUND);
		}

		for (String mdn : mdnList) {
			int lat = RandomLocationGenerator.randomLatitude();
			int lon = RandomLocationGenerator.randomLongitude();
			int ang = RandomLocationGenerator.randomAngle();
			log.info("creating lat, lon, ang: {}, {}, {}", lat, lon, ang);
			
			EmulatorInfo emulatorInfo = EmulatorInfo.create();
			GpsInfo gpsInfo = GpsInfo.create(lat, lon, ang, 0, 0.0);
			EmulatorInstance car = EmulatorInstance.create(mdn, emulatorInfo, gpsInfo, LocalDateTime.now());

			instances.add(car);
		}

		return instances;
	}

	//세션 초기화 시 인스턴스 리스트 초기화
	public void resetUsedMdns() {
		instances.clear();
	}
}
