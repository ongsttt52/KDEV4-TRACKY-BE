package kernel360.trackyemulator.infrastructure.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CycleGpsRequest {

	private LocalDateTime oTime;
	private String gcd;
	private long lat;
	private long lon;
	private int ang;
	private int spd;
	private double sum;

	private CycleGpsRequest(String gcd, long lat, long lon, int ang, int spd, double sum) {
		this.oTime = LocalDateTime.now();
		this.gcd = gcd;
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}

	public static CycleGpsRequest of(long lat, long lon, int ang, int spd, double sum) {
		return new CycleGpsRequest(
			"A",
			lat,
			lon,
			ang,
			spd,
			sum
		);
	}
}
