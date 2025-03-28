package kernel360.trackyemulator.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyemulator.application.service.CarInstanceFactory.SingleCarInstanceFactory;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarInstanceManager {

	private final SingleCarInstanceFactory singleCarInstanceFactory;

	private List<EmulatorInstance> instances = new ArrayList<>();

	// 뷰에서 입력받은 에뮬레이터 개수만큼 인스턴스 생성
	public void configureCount(int count) {
		if (count == 1) {
			this.instances = singleCarInstanceFactory.createCarInstances();
		} else {
			throw new UnsupportedOperationException("지금은 1대만 지원합니다.");
		}
	}

	//시동 ON 정보 전송
	public void sendStartRequests() {

	}

}
