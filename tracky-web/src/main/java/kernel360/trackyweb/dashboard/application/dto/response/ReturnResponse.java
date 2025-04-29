package kernel360.trackyweb.dashboard.application.dto.response;

import java.time.LocalDateTime;

public record ReturnResponse(
	String rentUuid,
	String renterName,
	String rentStatus,
	LocalDateTime rentEtime,
	String mdn,
	String carPlate,
	String carType
) {
}
