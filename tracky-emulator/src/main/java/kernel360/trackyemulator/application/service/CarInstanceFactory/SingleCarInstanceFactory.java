package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SingleCarInstanceFactory {

	private final RandomLocationGenerator locationGenerator;

	public List<EmulatorInstance> createCarInstances() {
		String mdn = "0077184075";
		int lat = locationGenerator.randomLatitude();
		int lon = locationGenerator.randomLongitude();
		int ang = locationGenerator.randomAngle();

		EmulatorInfo emulatorInfo = EmulatorInfo.create(mdn);
		EmulatorInstance car = EmulatorInstance.create(emulatorInfo, lat, lon, LocalDateTime.now(), ang);

		List<EmulatorInstance> instances = new ArrayList<>();
		instances.add(car);
		return instances;
	}
}
