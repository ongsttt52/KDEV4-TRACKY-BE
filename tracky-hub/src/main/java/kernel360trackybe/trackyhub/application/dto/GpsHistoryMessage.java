package kernel360trackybe.trackyhub.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GpsHistoryMessage {
	private String mdn;    // 차량 번호
	private String tid;    // 터미널 아이디
	private String mid;    // 제조사 아이디
	private String pv;     // 패킷 버전
	private String did;    // 디바이스 아이디
	private LocalDateTime oTime;  // 발생시간

	// Cycle specific data
	private int sec;    // 발생시간 '초'
	private String gcd;    // GPS 상태
	private long lat;    // GPS 위도
	private long lon;    // GPS 경도
	private String ang;    // 방향
	private String spd;    // 속도

	private GpsHistoryMessage(String mdn, String tid, String mid, String pv, String did,
		LocalDateTime oTime, int sec, String gcd, long lat, long lon,
		String ang, String spd) {
		this.mdn = mdn;
		this.tid = tid;
		this.mid = mid;
		this.pv = pv;
		this.did = did;
		this.oTime = oTime;
		this.sec = sec;
		this.gcd = gcd;
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
	}

	public static GpsHistoryMessage from(CycleInfoRequest carInfo, CycleGpsRequest cycleGpsRequest) {
		return new GpsHistoryMessage(
			carInfo.getMdn(),
			carInfo.getTid(),
			carInfo.getMid(),
			carInfo.getPv(),
			carInfo.getDid(),
			carInfo.getOTime(),
			cycleGpsRequest.getSec(),
			cycleGpsRequest.getGcd(),
			cycleGpsRequest.getLat(),
			cycleGpsRequest.getLon(),
			cycleGpsRequest.getAng(),
			cycleGpsRequest.getSpd()
		);
	}
}