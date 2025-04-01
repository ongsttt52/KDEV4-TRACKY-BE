package kernel360.trackyemulator.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CycleInfoRequest {

    private String mdn;    // 차량 번호
    private String tid;    // 터미널 아이디
    private String mid;    // 제조사 아이디
    private String pv;     // 패킷 버전
    private String did;    // 디바이스 아이디

    @JsonProperty("cCnt")
    private final int cCnt;   // 주기 정보 개수

    @JsonFormat(pattern = "yyyyMMddHHmm")
    private final LocalDateTime oTime;  // 발생시간

    @JsonProperty("cList")
    private final List<CycleGpsRequest> cList;  // 주기정보 리스트


    private CycleInfoRequest(String mdn, String tid, String mid, String pv, String did,
                             int cCnt, LocalDateTime oTime, List<CycleGpsRequest> cList) {
        this.mdn = mdn;
        this.tid = tid;
        this.mid = mid;
        this.pv = pv;
        this.did = did;
        this.cCnt = cCnt;
        this.oTime = oTime;
        this.cList = cList;
    }

    public static CycleInfoRequest of(String mdn, String tid, String mid, String pv, String did,
                                      LocalDateTime oTime, List<CycleGpsRequest> cList) {
        return new CycleInfoRequest(
                mdn,
                tid,
                mid,
                pv,
                did,
                cList.size(),
                oTime,
                cList
        );
    }
}