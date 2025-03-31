package kernel360.trackyweb.rent.application.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.RentEntity;

public record RentResponse(
	Long id,
	String rent_uuid,
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
	public static RentResponse from(RentEntity rent) {
		return new RentResponse(
			rent.getId(),               // id
			rent.getRentUuid(),         // rent_uuid
			rent.getMdn(),              // mdn
			rent.getRenterName(),       // renterName
			rent.getRenterPhone(),      // renterPhone
			rent.getRentStime(),        // rentStime
			rent.getRentEtime(),        // rentEtime
			rent.getRentLoc(),          // rentLoc
			rent.getReturnLoc(),        // returnLoc
			rent.getPurpose(),          // purpose
			rent.getRentStatus()        // rentStatus
		);
	}
}