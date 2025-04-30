package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;

@JsonIgnoreProperties(value = {"emulatorInfo"})
public record CycleInfoRequest(
	String mdn,
	EmulatorInfo emulatorInfo,
	int cCnt,
	LocalDateTime oTime,
	List<CycleGpsRequest> cList
) {

	@JsonCreator
	public CycleInfoRequest(
		@JsonProperty("mdn") String mdn,
		@JsonProperty("tid") String tid,
		@JsonProperty("mid") String mid,
		@JsonProperty("did") String did,
		@JsonProperty("pv") String pv,
		@JsonProperty("cCnt") int cCnt,
		@JsonProperty("oTime") @JsonFormat(pattern = "yyyyMMddHHmm") LocalDateTime oTime,
		@JsonProperty("cList") List<CycleGpsRequest> cList
	) {
		this(mdn, EmulatorInfo.create(tid, mid, did, pv), cCnt, oTime, cList);
	}

	public static CycleInfoRequest of(String mdn, EmulatorInfo emulatorInfo, LocalDateTime oTime,
		List<CycleGpsRequest> cList) {
		return new CycleInfoRequest(
			mdn,
			emulatorInfo,
			cList.size(),
			oTime,
			cList
		);
	}

	// EmulatorInfo의 필드들을 평면화하기 위한 JsonGetter 메서드들
	@JsonGetter("tid")
	public String getTid() {
		return emulatorInfo.getTid();
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

	// oTime을 원하는 포맷으로 직렬화
	@JsonGetter("oTime")
	public String getFormattedOTime() {
		if (oTime != null) {
			return oTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		}
		return null;
	}
}
