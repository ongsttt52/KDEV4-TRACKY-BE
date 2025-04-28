package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;

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
		@JsonProperty("oTime") @JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime oTime,
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
}
