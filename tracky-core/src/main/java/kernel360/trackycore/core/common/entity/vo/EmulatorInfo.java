package kernel360.trackycore.core.common.entity.vo;

import lombok.Getter;

@Getter
public class EmulatorInfo {
	private final String mdn;    // 차량 식별 Key
	private final String tid;    // 터미널 아이디 - 'A001'로 고정
	private final String mid;    // 제조사 아이디 - CNSLink는 '6' 값 사용
	private final String pv;     // 패킷 버전 - '5' 고정
	private final String did;    // 디바이스 아이디 - GPS로만 운영함으로 '1'로 고정
	private final String gcd;    // GPS 상태 - 'A' 고정

	private EmulatorInfo(String mdn, String tid, String mid, String pv, String did, String gcd) {
		this.mdn = mdn;
		this.tid = tid;
		this.mid = mid;
		this.pv = pv;
		this.did = did;
		this.gcd = gcd;
	}

	public static EmulatorInfo create(String mdn) {
		return new EmulatorInfo(mdn, "A001", "6", "5", "1", "A");
	}
}
