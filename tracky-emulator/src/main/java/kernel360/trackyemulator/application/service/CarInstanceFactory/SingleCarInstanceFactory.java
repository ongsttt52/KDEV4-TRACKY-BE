package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackycore.core.domain.vo.GpsInfo;
import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;

@Component
public class SingleCarInstanceFactory {

	public List<EmulatorInstance> createCarInstances() {
		String mdn = "0077184075";
		int lat = RandomLocationGenerator.randomLatitude();
		int lon = RandomLocationGenerator.randomLongitude();
		int ang = RandomLocationGenerator.randomAngle();

		EmulatorInfo emulatorInfo = EmulatorInfo.create();
		GpsInfo gpsInfo = GpsInfo.create(lat, lon, ang, 0, 0.0);
		EmulatorInstance car = EmulatorInstance.create(mdn, emulatorInfo, gpsInfo, LocalDateTime.now());

		List<EmulatorInstance> instances = new ArrayList<>();
		instances.add(car);
		return instances;
	}
}
