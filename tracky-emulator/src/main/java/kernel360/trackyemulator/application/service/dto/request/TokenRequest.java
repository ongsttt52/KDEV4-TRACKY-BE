package kernel360.trackyemulator.application.service.dto.request;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;

public record TokenRequest(String mdn, EmulatorInfo emulatorInfo) {

	/**
	 * EmulatorInstance 기반 TokenRequest 생성
	 */
	public static TokenRequest toRequest(String mdn, EmulatorInfo emulatorInfo) {
		return new TokenRequest(mdn, emulatorInfo);
	}
}
