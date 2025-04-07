package kernel360.trackyweb.reservation.presentation.dto;

import java.time.LocalDateTime;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.LocationEntity;

public record ReservationDrivingsResponse(
	LocalDateTime driveOnTime,    //주행 시작시간
	LocalDateTime driveOffTim,    //주행 종료 시간
	double driveDistance,    //주행 별 거리
	int driveStartLon,    //시작 위도
	int driveStartLat,    //시작 경도
	int driveEndLon,    //종료 위도
	int driveEndLat //종료 경도

) {
	public static ReservationDrivingsResponse fromEntity(DriveEntity driveEntity, LocationEntity locationEntity) {
		return new ReservationDrivingsResponse(
			driveEntity.getDriveOnTime(),
			driveEntity.getDriveOffTime(),
			driveEntity.getDriveDistance(),
			locationEntity.getDriveStartLon(),
			locationEntity.getDriveStartLat(),
			locationEntity.getDriveEndLon(),
			locationEntity.getDriveEndLat()
		);
	}
}
