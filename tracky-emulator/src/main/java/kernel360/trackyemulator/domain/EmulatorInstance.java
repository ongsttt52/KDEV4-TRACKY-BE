package kernel360.trackyemulator.domain;

import kernel360.trackyemulator.infrastructure.dto.CycleGpsRequest;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class EmulatorInstance {

	private final String mdn;    // 차량 식별 Key
	private final String tid;    // 터미널 아이디 - 'A001'로 고정
	private final String mid;    // 제조사 아이디 - CNSLink는 '6' 값 사용
	private final String pv;     // 패킷 버전 - '5' 고정
	private final String did;    // 디바이스 아이디 - GPS로만 운영함으로 '1'로 고정
	private final String gcd;    // GPS 상태 - 'A' 고정

	private String token;  // 단말 인증 토큰

	private double sum;             // 시동 ON 후 총 누적 거리

	private int cycleLastLat;    // 60초 주기 데이터 중 마지막 60번째 GPS 위도
	private int cycleLastLon;    // 60초 주기 데이터 중 마지막 60번째 GPS 경도
	private int cycleLastSpeed;    //60초 주기 데이터 중 마지막 속도
	private int cycleLastAng;        //60초 주기 데이터 중 마지막 방향

	private final LocalDateTime carOnTime;
	private LocalDateTime carOffTime;

	private final List<CycleGpsRequest> cycleBuffer = new ArrayList<>();

	private EmulatorInstance(String mdn, String tid, String mid, String pv, String did, String gcd, int cycleLastLat,
		int cycleLastLon, LocalDateTime carOnTime) {
		this.mdn = mdn;
		this.tid = tid;
		this.mid = mid;
		this.pv = pv;
		this.did = did;
		this.gcd = gcd;
		this.sum = 0;
		this.cycleLastLat = cycleLastLat;
		this.cycleLastLon = cycleLastLon;
		this.carOnTime = carOnTime;
	}

	public static EmulatorInstance create(String mdn, String tid, String mid, String pv, String did, String gcd,
		int cycleLastLat, int cycleLastLon, LocalDateTime carOnTime) {
		return new EmulatorInstance(mdn, tid, mid, pv, did, gcd, cycleLastLat, cycleLastLon, carOnTime);
	}

	//토큰 세팅
	public void setToken(String token) {
		this.token = token;
	}

	public void setCarOffTime(LocalDateTime carOffTime) {
		this.carOffTime = carOffTime;
	}

	// 주기 데이터 정보 & 누적 거리 한번에 업데이트
	public void updateCycleInfo(int lat, int lon, int speed, int ang, double distance) {
		this.sum += distance;
		this.cycleLastLat = lat;
		this.cycleLastLon = lon;
		this.cycleLastSpeed = speed;
		this.cycleLastAng = ang;
	}

	public void setInitLocation(int lat, int lon) {
		this.cycleLastLon = lon;
		this.cycleLastLat = lat;
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