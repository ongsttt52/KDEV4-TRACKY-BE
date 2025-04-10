package kernel360.trackyweb.drivehistory.domain;

import java.time.LocalDateTime;

public record GpsData(int lat, int lon, int spd, LocalDateTime o_time) {
	public static GpsData create(int lat, int lon, int spd, LocalDateTime o_time) {
		return new GpsData(lat, lon, spd, o_time);
	}
}
