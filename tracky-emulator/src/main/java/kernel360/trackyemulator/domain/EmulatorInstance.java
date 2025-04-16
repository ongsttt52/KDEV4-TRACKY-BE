package kernel360.trackyemulator.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackyemulator.application.service.dto.request.CycleGpsRequest;
import lombok.Getter;

@Getter
public class EmulatorInstance {

	private final EmulatorInfo emulatorInfo;

	private String token;  // 단말 인증 토큰

	private double sum;             // 시동 ON 후 총 누적 거리

	private int cycleLastLat;    // 60초 주기 데이터 중 마지막 60번째 GPS 위도
	private int cycleLastLon;    // 60초 주기 데이터 중 마지막 60번째 GPS 경도
	private int cycleLastSpeed;    //60초 주기 데이터 중 마지막 속도
	private int ang;        //방향

	private final LocalDateTime carOnTime;
	private LocalDateTime carOffTime;

	private final List<CycleGpsRequest> cycleBuffer = new ArrayList<>();

	private EmulatorInstance(EmulatorInfo emulatorInfo, int cycleLastLat, int cycleLastLon, LocalDateTime carOnTime,
		int ang) {
		this.emulatorInfo = emulatorInfo;
		this.sum = 0;
		this.cycleLastLat = cycleLastLat;
		this.cycleLastLon = cycleLastLon;
		this.carOnTime = carOnTime;
		this.ang = ang;
	}

	public static EmulatorInstance create(EmulatorInfo emulatorInfo, int cycleLastLat, int cycleLastLon,
		LocalDateTime carOnTime, int ang) {
		return new EmulatorInstance(emulatorInfo, cycleLastLat, cycleLastLon, carOnTime, ang);
	}

	//토큰 세팅
	public void setToken(String token) {
		this.token = token;
	}

	public void setCarOffTime(LocalDateTime carOffTime) {
		this.carOffTime = carOffTime;
	}

	// 주기 데이터 정보 & 누적 거리 한번에 업데이트
	public void updateCycleInfo(int lat, int lon, int speed, double distance) {
		this.sum += distance;
		this.cycleLastLat = lat;
		this.cycleLastLon = lon;
		this.cycleLastSpeed = speed;
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
}
