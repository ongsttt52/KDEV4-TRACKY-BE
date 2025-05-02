package kernel360.trackyweb.rent.application.dto.request;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public record RentUpdateRequest(
	String mdn,
	String renterName,
	String renterPhone,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	String rentLoc,
	String returnLoc,
	String purpose,
	RentStatus rentStatus
) {

}
