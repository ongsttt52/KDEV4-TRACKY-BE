package kernel360.trackyweb.drive.domain.vo;

import java.time.LocalDateTime;
import java.util.List;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public record DriveHistory(
	Long driveId,
	String rentUuid,
	String mdn,
	String carPlate,
	int onLat,
	int onLon,
	int offLat,
	int offLon,
	double driveDistance,
	String renterName,
	String renterPhone,
	RentStatus rentStatus,
	CarStatus carStatus,
	String purpose,
	LocalDateTime driveOnTime,
	LocalDateTime driveOffTime,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	List<GpsData> gpsDataList
) {
	public static DriveHistory create(
		DriveEntity drive,
		List<GpsData> gpsData
	) {
		return new DriveHistory(
			drive.getId(),
			drive.getRent().getRentUuid(),
			drive.getCar().getMdn(),
			drive.getCar().getCarPlate(),
			drive.getLocation().getDriveStartLat(),
			drive.getLocation().getDriveStartLon(),
			drive.getLocation().getDriveEndLat(),
			drive.getLocation().getDriveEndLon(),
			drive.getDriveDistance(),
			drive.getRent().getRenterName(),
			drive.getRent().getRenterPhone(),
			drive.getRent().getRentStatus(),
			drive.getCar().getStatus(),
			drive.getRent().getPurpose(),
			drive.getDriveOnTime(),
			drive.getDriveOffTime(),
			drive.getRent().getRentStime(),
			drive.getRent().getRentEtime(),
			gpsData
		);
	}
}
