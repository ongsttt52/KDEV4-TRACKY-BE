package kernel360.trackyemulator.presentation.dto;

import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.Getter;

@Getter
public class TokenRequest {

    private String mdn;    // 차량 번호
    private String tid;    // 터미널 아이디
    private String mid;    // 제조사 아이디
    private String pv;     // 패킷 버전
    private String did;    // 디바이스 아이디

    private TokenRequest(String mdn, String tid, String mid, String pv, String did) {
        this.mdn = mdn;
        this.tid = tid;
        this.mid = mid;
        this.pv = pv;
        this.did = did;
    }

    /**
     * EmulatorInstance 기반 TokenRequest 생성
     */
    public static TokenRequest from(EmulatorInstance car) {
        return new TokenRequest(
                car.getMdn(),
                car.getTid(),
                car.getMid(),
                car.getPv(),
                car.getDid()
        );
    }
}