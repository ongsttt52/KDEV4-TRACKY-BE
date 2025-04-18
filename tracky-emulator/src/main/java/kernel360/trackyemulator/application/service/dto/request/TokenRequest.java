package kernel360.trackyemulator.application.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;

public record TokenRequest(String mdn, EmulatorInfo emulatorInfo) {

	@JsonCreator
	public TokenRequest(
		@JsonProperty("mdn") String mdn,
		@JsonProperty("tid") String tid,
		@JsonProperty("mid") String mid,
		@JsonProperty("did") String did,
		@JsonProperty("pv") String pv
	) {
		this(mdn, EmulatorInfo.create(tid, mid, did, pv));
	}

	/**
	 * EmulatorInstance 기반 TokenRequest 생성
	 */
	public static TokenRequest toRequest(String mdn, EmulatorInfo emulatorInfo) {
		return new TokenRequest(mdn, emulatorInfo);
	}
}
