package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import lombok.Getter;

@Getter
public class CycleInfoRequest {

	private EmulatorInfo emulatorInfo;

	@JsonProperty("cCnt")
	private final int cCnt;   // 주기 정보 개수

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private final LocalDateTime oTime;  // 발생시간

	@JsonProperty("cList")
	private final List<CycleGpsRequest> cList;  // 주기정보 리스트

	private CycleInfoRequest(EmulatorInfo emulatorInfo, int cCnt, LocalDateTime oTime, List<CycleGpsRequest> cList) {
		this.emulatorInfo = emulatorInfo;
		this.cCnt = cCnt;
		this.oTime = oTime;
		this.cList = cList;
	}

	public static CycleInfoRequest of(EmulatorInfo emulatorInfo, LocalDateTime oTime, List<CycleGpsRequest> cList) {
		return new CycleInfoRequest(
			emulatorInfo,
			cList.size(),
			oTime,
			cList
		);
	}
}
