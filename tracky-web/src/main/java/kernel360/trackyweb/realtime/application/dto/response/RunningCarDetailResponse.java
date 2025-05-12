package kernel360.trackyweb.realtime.application.dto.response;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;

public record RunningCarDetailResponse(
	Long id,
	String mdn,
	String carPlate,
	String carName,
	LocalDate date,
	String renterName,
	double distance,
	String drivingTime,
	LocalDateTime driveOnTime,
	CarStatus status,
	int lat,
	int lon
) {
	public static RunningCarDetailResponse from(DriveEntity drive, Double sum) {
		Long id = drive.getId();
		String mdn = drive.getCar().getMdn();
		String plate = drive.getCar().getCarPlate();
		String carName = drive.getCar().getCarName();
		LocalDate date = LocalDate.now();
		String renter = drive.getRent().getRenterName();
		double distance = sum;

		LocalDateTime driveOnTime = drive.getDriveOnTime();
		Duration duration = Duration.between(driveOnTime, LocalDateTime.now());
		String drivingTime = formatDuration(duration);
		CarStatus status = drive.getCar().getStatus();

		int lat = drive.getLocation().getDriveStartLat();
		int lon = drive.getLocation().getDriveStartLon();
		;

		return new RunningCarDetailResponse(id, mdn, plate, carName, date, renter, distance, drivingTime, driveOnTime,
			status, lat, lon);

	}

	private static String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutesPart();
		long seconds = duration.toSecondsPart();

		if (hours > 0) {
			return String.format("%dh %02dm", hours, minutes);
		} else if (minutes > 0) {
			return String.format("%dm %02ds", minutes, seconds);
		} else {
			return String.format("%ds", seconds);
		}
	}
}
