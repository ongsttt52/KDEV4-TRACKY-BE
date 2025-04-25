package kernel360.trackyweb.drive.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public record DriveListResponse(
	Long id,
	String mdn,
	String carPlate,
	String rentUuid,
	String renterName,
	String purpose,
	Double driveDistance,
	LocalDateTime driveOnTime,
	LocalDateTime driveOffTime,
	Integer driveEndLat,
	Integer driveEndLon
) {
	public static DriveListResponse toResponse(DriveEntity drive) {
		return new DriveListResponse(
			drive.getId(),
			drive.getCar().getMdn(),
			drive.getCar().getCarPlate(),
			drive.getRent().getRentUuid(),
			drive.getRent().getRenterName(),
			drive.getRent().getPurpose(),
			drive.getDriveDistance(),
			drive.getDriveOnTime(),
			drive.getDriveOffTime(),
			drive.getLocation().getDriveEndLat(),
			drive.getLocation().getDriveEndLon()
		);
	}
}
