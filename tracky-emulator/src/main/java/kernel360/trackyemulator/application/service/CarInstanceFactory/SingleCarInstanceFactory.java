package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

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

		EmulatorInstance car = EmulatorInstance.create(mdn, "A001", "6", "5", "1", "A", lat, lon, LocalDateTime.now(),
			ang);

		List<EmulatorInstance> instances = new ArrayList<>();
		instances.add(car);
		return instances;
	}

}
