package kernel360.trackyemulator.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;
import kernel360.trackyemulator.application.service.dto.request.CycleGpsRequest;
import lombok.Getter;

@Getter
public class EmulatorInstance {

	private final EmulatorInfo emulatorInfo;
	private GpsInfo cycleLastGpsInfo;

	private final String mdn;
	private String token;        // 단말 인증 토큰
	private double totalSum;    // 시동 ON 후 총 누적 거리

	private final LocalDateTime carOnTime;
	private LocalDateTime carOffTime;

	private final List<CycleGpsRequest> cycleBuffer = new ArrayList<>();

	private EmulatorInstance(String mdn, EmulatorInfo emulatorInfo, GpsInfo cycleLastGpsInfo, LocalDateTime carOnTime) {
		this.mdn = mdn;
		this.emulatorInfo = emulatorInfo;
		this.cycleLastGpsInfo = cycleLastGpsInfo;
		this.carOnTime = carOnTime;
	}

	public static EmulatorInstance create(String mdn, EmulatorInfo emulatorInfo, GpsInfo cycleLastGpsInfo,
		LocalDateTime carOnTime) {
		return new EmulatorInstance(mdn, emulatorInfo, cycleLastGpsInfo, carOnTime);
	}

	//토큰 세팅
	public void setToken(String token) {
		this.token = token;
	}

	public void setCarOffTime(LocalDateTime carOffTime) {
		this.carOffTime = carOffTime;
	}

	// 주기 데이터 정보 & 누적 거리 한번에 업데이트
	public void updateCycleInfo(GpsInfo cycleLastGpsInfo) {
		this.cycleLastGpsInfo = cycleLastGpsInfo;
		this.totalSum += cycleLastGpsInfo.getSum();
	}

	public void addCycleData(CycleGpsRequest data) {
		cycleBuffer.add(data);
	}

	//주기 데이터 60개가 모였는지
	public boolean isBufferFull() {
		return cycleBuffer.size() >= 60;
	}

	public List<CycleGpsRequest> clearBuffer() {
		List<CycleGpsRequest> copy = new ArrayList<>(cycleBuffer);
		cycleBuffer.clear();
		return copy;
	}

	public GpsInfo offGpsInfo() {
		return GpsInfo.create(
			this.cycleLastGpsInfo.getLat(),
			this.cycleLastGpsInfo.getLon(),
			this.cycleLastGpsInfo.getAng(),
			this.cycleLastGpsInfo.getSpd(),
			this.totalSum);
	}
}
