package kernel360.trackyweb.realtime.application.dto.response;

import java.time.Duration;
import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public record LiveCarResponse (
	String mdn,
	String carPlate,
	String renterName,
	double distance,
	String drivingTime,
	String status
) {
	public static LiveCarResponse from(DriveEntity entity) {
		String mdn = entity.getCar().getMdn();
		String plate = entity.getCar().getCarPlate();
		String renter = entity.getRent().getRenterName();
		double distance = entity.getDriveDistance();

		LocalDateTime driveOn = entity.getDriveOnTime();
		Duration duration = Duration.between(driveOn, LocalDateTime.now());
		String drivingTime = formatDuration(duration);

		String status = entity.getCar().getStatus();

		return new LiveCarResponse(mdn, plate, renter, distance, drivingTime, status);

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

