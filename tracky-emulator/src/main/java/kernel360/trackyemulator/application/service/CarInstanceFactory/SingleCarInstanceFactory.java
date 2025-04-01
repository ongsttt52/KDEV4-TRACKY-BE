package kernel360.trackyemulator.application.service.CarInstanceFactory;

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
		String mdn = "KMHXX00XXXX123456";
		long lat = locationGenerator.randomLatitude();
		long lon = locationGenerator.randomLongitude();

		EmulatorInstance car = EmulatorInstance.create(mdn, "A001", "6", "5", "1", "A", lat, lon);

		List<EmulatorInstance> instances = new ArrayList<>();
		instances.add(car);
		return instances;
	}

}
