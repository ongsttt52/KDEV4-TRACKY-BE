package kernel360.trackyconsumer.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;

public record CycleInfoRequest(
	EmulatorInfo emulatorInfo,
	String mdn,

	int cCnt,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime oTime,

	List<CycleGpsRequest> cList
) {
	@JsonCreator
	public CycleInfoRequest(
		@JsonProperty String tid,
		@JsonProperty String mid,
		@JsonProperty String pv,
		@JsonProperty String did,
		@JsonProperty("mdn") String mdn,
		@JsonProperty("cCnt") int cCnt,
		@JsonProperty("oTime") @JsonFormat(pattern = "yyyyMMddHHmm") LocalDateTime oTime,
		@JsonProperty("cList") List<CycleGpsRequest> cList
	) {
		this(EmulatorInfo.create(tid, mid, pv, did), mdn, cCnt, oTime, cList);
	}
}
