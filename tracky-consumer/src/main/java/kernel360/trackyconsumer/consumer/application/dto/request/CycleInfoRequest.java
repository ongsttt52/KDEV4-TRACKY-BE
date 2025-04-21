package kernel360.trackyconsumer.consumer.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;

public record CycleInfoRequest(
	EmulatorInfo emulatorInfo,
	String mdn,

	int cCnt,

	LocalDateTime oTime,

	List<CycleGpsRequest> cList
) {
	@JsonCreator
	public CycleInfoRequest(
		@JsonProperty String tid,
		@JsonProperty String mid,
		@JsonProperty String pv,
		@JsonProperty String did,
		@JsonProperty String mdn,
		@JsonProperty int cCnt,
		@JsonProperty @JsonFormat(pattern = "yyyyMMddHHmm") LocalDateTime oTime,
		@JsonProperty List<CycleGpsRequest> cList
	) {
		this(EmulatorInfo.create(tid, mid, pv, did), mdn, cCnt, oTime, cList);
	}
}
