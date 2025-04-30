package kernel360.trackyemulator.application.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;

@JsonIgnoreProperties(value = {"emulatorInfo"})
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

	// EmulatorInfo의 필드들을 평면화하기 위한 JsonGetter 메서드들
	@JsonGetter("tid")
	public String getTid() {
		return emulatorInfo.getTid(); // Lombok @Getter 메서드 사용
	}

	@JsonGetter("mid")
	public String getMid() {
		return emulatorInfo.getMid();
	}

	@JsonGetter("did")
	public String getDid() {
		return emulatorInfo.getDid();
	}

	@JsonGetter("pv")
	public String getPv() {
		return emulatorInfo.getPv();
	}
}
