package kernel360.trackyweb.admin.search.application.response;

import java.time.Duration;
import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;

public record AdminRunningCarResponse(
	Long id,
	String mdn,
	String carPlate,
	String renterName,
	double distance,
	long drivingTime,
	CarStatus status,
	String bizName
) {
	public static AdminRunningCarResponse from(DriveEntity entity) {
		Long id = entity.getId();
		String mdn = entity.getCar().getMdn();
		String plate = entity.getCar().getCarPlate();
		String renter = entity.getRent().getRenterName();
		double distance = entity.getDriveDistance();
		String bizName = entity.getCar().getBiz().getBizName();

		LocalDateTime driveOn = entity.getDriveOnTime();
		Duration duration = Duration.between(driveOn, LocalDateTime.now());
		long drivingTime = duration.toSeconds();

		CarStatus status = entity.getCar().getStatus();

		return new AdminRunningCarResponse(id, mdn, plate, renter, distance, drivingTime, status, bizName);

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
