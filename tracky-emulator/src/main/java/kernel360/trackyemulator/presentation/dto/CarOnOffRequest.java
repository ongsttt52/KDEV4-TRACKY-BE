package kernel360.trackyemulator.presentation.dto;

import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CarOnOffRequest {

    private String mdn;
    private String tid;
    private String mid;
    private String pv;
    private String did;

    private LocalDateTime onTime;
    private LocalDateTime offTime;

    private String gcd;
    private long lat;
    private long lon;
    private int ang;
    private int spd;
    private double sum;

    private CarOnOffRequest(String mdn, String tid, String mid, String pv, String did,
                            LocalDateTime onTime, LocalDateTime offTime,
                            String gcd, long lat, long lon, int ang, int spd, double sum) {
        this.mdn = mdn;
        this.tid = tid;
        this.mid = mid;
        this.pv = pv;
        this.did = did;
        this.onTime = onTime;
        this.offTime = offTime;
        this.gcd = gcd;
        this.lat = lat;
        this.lon = lon;
        this.ang = ang;
        this.spd = spd;
        this.sum = sum;
    }

    /**
     * 시동 ON DTO 생성 (EmulatorInstance + Random 위치)
     */
    public static CarOnOffRequest ofOn(EmulatorInstance car, long lat, long lon) {
        return new CarOnOffRequest(
                car.getMdn(),
                car.getTid(),
                car.getMid(),
                car.getPv(),
                car.getDid(),
                LocalDateTime.now(), // onTime
                null,               // offTime
                "A",
                lat,
                lon,
                0,
                0,
                0
        );
    }

    /**
     * 시동 OFF DTO 생성 (EmulatorInstance 상태 기반)
     */
    public static CarOnOffRequest ofOff(EmulatorInstance car) {
        return new CarOnOffRequest(
                car.getMdn(),
                car.getTid(),
                car.getMid(),
                car.getPv(),
                car.getDid(),
                null,               // onTime
                LocalDateTime.now(), // offTime
                "A",
                car.getCycleLastLat(),
                car.getCycleLastLon(),
                car.getCycleLastAng(),
                car.getCycleLastSpeed(),
                car.getSum()
        );
    }
}