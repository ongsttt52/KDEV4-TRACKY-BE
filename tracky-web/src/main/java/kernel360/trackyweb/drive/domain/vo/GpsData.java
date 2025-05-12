package kernel360.trackyweb.drive.domain.vo;

import java.time.LocalDateTime;

public record GpsData(
	int lat,
	int lon,
	int spd,
	int ang,
	LocalDateTime o_time
) {
	public static GpsData create(int lat, int lon, int spd, int ang, LocalDateTime o_time) {
		return new GpsData(lat, lon, spd, ang, o_time);
	}
}
