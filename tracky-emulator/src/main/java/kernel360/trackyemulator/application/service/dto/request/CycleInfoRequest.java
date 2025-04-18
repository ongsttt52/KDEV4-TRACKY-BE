package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;

public record CycleInfoRequest(
	String mdn,
	EmulatorInfo emulatorInfo,

	@JsonProperty("cCnt")
	int cCnt,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime oTime,

	@JsonProperty("cList")
	List<CycleGpsRequest> cList

) {
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
