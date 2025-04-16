package kernel360.trackycore.core.common.entity.vo;

import lombok.Getter;

@Getter
public class GpsInfo {
	private final int lat;
	private final int lon;
	private final int ang;
	private final int spd;
	private final double sum;

	private GpsInfo(int lat, int lon, int ang, int spd, double sum) {
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}

	public static GpsInfo create(int lat, int lon, int ang, int spd, double sum) {
		return new GpsInfo(lat, lon, ang, spd, sum);
	}
}
