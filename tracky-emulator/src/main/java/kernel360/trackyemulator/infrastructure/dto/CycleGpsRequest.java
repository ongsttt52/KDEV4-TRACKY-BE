package kernel360.trackyemulator.infrastructure.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CycleGpsRequest {

    private int sec;
    private String gcd;
    private long lat;
    private long lon;
    private int ang;
    private int spd;
    private double sum;

    private CycleGpsRequest(int sec, String gcd, long lat, long lon, int ang, int spd, double sum) {
        this.sec = sec;
        this.gcd = gcd;
        this.lat = lat;
        this.lon = lon;
        this.ang = ang;
        this.spd = spd;
        this.sum = sum;
    }

    public static CycleGpsRequest of(long lat, long lon, int ang, int spd, double sum) {
        return new CycleGpsRequest(
                LocalDateTime.now().getSecond(),
                "A",
                lat,
                lon,
                ang,
                spd,
                sum
        );
    }
}