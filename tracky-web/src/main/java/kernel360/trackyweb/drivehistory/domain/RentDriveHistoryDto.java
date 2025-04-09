package kernel360.trackyweb.drivehistory.domain;

import java.time.LocalDateTime;

public record RentDriveHistoryDto(
	String rentUuid,
	String renterName,
	String mdn,
	LocalDateTime rentStime,
	LocalDateTime rentEtime
) {
}
