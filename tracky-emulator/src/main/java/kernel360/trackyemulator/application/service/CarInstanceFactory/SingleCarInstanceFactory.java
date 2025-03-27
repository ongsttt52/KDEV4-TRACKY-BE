package kernel360.trackyemulator.application.service.CarInstanceFactory;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyemulator.application.service.client.StartRequestClient;
import kernel360.trackyemulator.application.service.client.TokenRequestClient;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SingleCarInstanceFactory {

	private final TokenRequestClient getTokenClient; // 서버에서 토큰 받아오는 클라이언트
	private final StartRequestClient startRequestClient; // 시동ON 전송

	public List<EmulatorInstance> createCarInstances() {
		String mdn = "01012345678";
		String ctrId = "CTR123";

		//서버에서 토큰 & 거리 받아오기
		String token = getTokenClient.getTokenByMdn(mdn);

		//EmulatorInstance 생성
		EmulatorInstance car = EmulatorInstance.create(mdn, "A001", "6", "5", "1", "A", token);

		//시동 ON API 전송
		startRequestClient.sendStart(car, token);

		return null;
	}

}
