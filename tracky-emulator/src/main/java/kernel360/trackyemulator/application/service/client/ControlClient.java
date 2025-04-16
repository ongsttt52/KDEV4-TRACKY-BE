package kernel360.trackyemulator.application.service.client;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ControlClient {

	private final CycleRequestClient cycleRequestClient;
	private final MdnListRequestClient mdnListRequestClient;
	private final StartRequestClient startRequestClient;
	private final StopRequestClient stopRequestClient;
	private final TokenRequestClient tokenRequestClient;

	public ApiResponse sendCycleData(EmulatorInstance instance) {
		return cycleRequestClient.sendCycleData(instance);
	}

	public List<String> getMdnList() {
		return mdnListRequestClient.getMdnList();
	}

	public ApiResponse sendCarStart(EmulatorInstance instance) {
		return startRequestClient.sendCarStart(instance);
	}

	public ApiResponse sendCarStop(EmulatorInstance instance) {
		return stopRequestClient.sendCarStop(instance);
	}

	public String getToken(EmulatorInstance instance) {
		return tokenRequestClient.getToken(instance);
	}
}
