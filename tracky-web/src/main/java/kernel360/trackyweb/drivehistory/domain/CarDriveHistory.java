package kernel360.trackyweb.drivehistory.domain;

import java.time.LocalDateTime;

public record CarDriveHistory(
	Long driveId,
	String rentUuid,
	String mdn,
	int onLat,
	int onLon,
	int offLat,
	int offLon,
	double sum,
	LocalDateTime driveOnTime,
	LocalDateTime driveOffTime
) {
	public static CarDriveHistory create(
		Long driveId,
		String rentUuid,
		String mdn,
		int onLat,
		int onLon,
		int offLat,
		int offLon,
		double sum,
		LocalDateTime driveOnTime,
		LocalDateTime driveOffTime
	) {
		return new CarDriveHistory(
			driveId, rentUuid, mdn, onLat, onLon, offLat, offLon, sum,
			driveOnTime, driveOffTime
		);
	}
}
