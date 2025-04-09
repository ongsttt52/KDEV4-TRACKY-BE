package kernel360.trackyweb.drivehistory.domain;

import java.time.LocalDateTime;
import java.util.List;

public record DriveHistory(
	Long driveId,
	int onLat,
	int onLon,
	int offLat,
	int offLon,
	double sum,
	LocalDateTime driveOnTime,
	LocalDateTime driveOffTime,
	List<Coordinate> gpsCoordinates
) {
	public record Coordinate(int lat, int lon, int spd, LocalDateTime o_time) {
	}
}
