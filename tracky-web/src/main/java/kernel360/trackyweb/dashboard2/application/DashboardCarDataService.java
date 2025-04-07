package kernel360.trackyweb.dashboard2.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kernel360.trackyweb.dashboard2.domain.CarStatus;
import kernel360.trackyweb.dashboard2.infrastructure.repository.CarStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardCarDataService {

	private final CarStatusRepository carStatusRepository;

	public Map<String, Long> getAllCarStatus()  {
		List<CarStatus> grouped = carStatusRepository.findAllGroupedByStatus();

		Map<String, Long> result = new HashMap<>();
		for (CarStatus carStatus : grouped) {
			result.put(carStatus.getStatus(), carStatus.getCount());
		}

		return result;
	}

}
