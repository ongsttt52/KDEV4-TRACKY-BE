package kernel360.trackyemulator.application.service.dto.request;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;

public record TokenRequest(EmulatorInfo emulatorInfo) {

	/**
	 * EmulatorInstance 기반 TokenRequest 생성
	 */
	public static TokenRequest toRequest(EmulatorInfo emulatorInfo) {
		return new TokenRequest(emulatorInfo);
	}
}
