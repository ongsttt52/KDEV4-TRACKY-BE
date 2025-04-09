package kernel360.trackyweb.drivehistory.domain;

import java.time.LocalDateTime;
import java.util.List;

public record RentDriveHistory(
	String rentUuid,
	String renterName,
	String mdn,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	List<DrivelistDto> drivelist
) {
	public RentDriveHistory(String rentUuid, String renterName, String mdn,
		LocalDateTime rentStime, LocalDateTime rentEtime) {
		this(rentUuid, renterName, mdn, rentStime, rentEtime, null); // ← 기본 생성자
	}

	public record DrivelistDto(
		Long driveId,
		int onLat,
		int onLon,
		int offLat,
		int offLon,
		double sum,
		LocalDateTime driveOnTime,
		LocalDateTime driveOffTime
	) {
	}
}
