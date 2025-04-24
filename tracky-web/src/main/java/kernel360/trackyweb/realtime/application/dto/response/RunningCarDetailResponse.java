package kernel360.trackyweb.realtime.application.dto.response;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.DriveEntity;

//구지원 - 일단 detail api 완성 후, gpshistory를 이용해서 위치 가져왕

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
	String status
) {
	public static RunningCarDetailResponse from(DriveEntity entity) {
		Long id = entity.getId();
		String mdn = entity.getCar().getMdn();
		String plate = entity.getCar().getCarPlate();
		String carName = entity.getCar().getCarName();
		LocalDate date = LocalDate.now();
		String renter = entity.getRent().getRenterName();
		double distance = entity.getDriveDistance();

		LocalDateTime driveOnTime = entity.getDriveOnTime();
		Duration duration = Duration.between(driveOnTime, LocalDateTime.now());
		String drivingTime = formatDuration(duration);
		String status = entity.getCar().getStatus();

		return new RunningCarDetailResponse(id, mdn, plate, carName, date, renter, distance, drivingTime, driveOnTime,
			status);

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
