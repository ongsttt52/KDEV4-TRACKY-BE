package kernel360.trackyweb.rent.application.dto.request;

import java.time.LocalDateTime;

public record RentRequest(
	String mdn,
	String renterName,
	String renterPhone,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	String rentLoc,
	String returnLoc,
	String purpose,
	String rentStatus
) {

}
