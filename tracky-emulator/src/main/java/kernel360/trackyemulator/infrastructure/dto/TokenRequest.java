package kernel360.trackyemulator.infrastructure.dto;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.Getter;

@Getter
public class TokenRequest {

	private EmulatorInfo emulatorInfo;

	private TokenRequest(EmulatorInfo emulatorInfo) {
		this.emulatorInfo = emulatorInfo;
	}

	/**
	 * EmulatorInstance 기반 TokenRequest 생성
	 */
	public static TokenRequest toRequest(EmulatorInstance car) {
		return new TokenRequest(
			car.getEmulatorInfo()
		);
	}
}
