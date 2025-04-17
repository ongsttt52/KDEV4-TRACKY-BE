package kernel360.trackyconsumer.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;

public record CycleInfoRequest(
	EmulatorInfo emulatorInfo,
	String mdn,    // 차량 번호

	int cCnt,   // 주기 정보 개수

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime oTime,  // 발생시간

	List<CycleGpsRequest> cList  // 주기정보 리스트
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
