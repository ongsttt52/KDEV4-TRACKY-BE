package kernel360.trackyweb.rent.application.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.common.entity.RentEntity;

public record RentRequest(
	String mdn,
	String renterName,
	String renterPhone,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	String rentLoc,
	String returnLoc,
	String purpose
) {
	public RentEntity toEntity(String rentUuid, String rentStatus) {
		return RentEntity.create(
			mdn,
			rentUuid,
			rentStime,
			rentEtime,
			renterName,
			renterPhone,
			purpose,
			rentStatus,
			rentLoc,
			null,  // rentLat
			null,  // rentLon
			returnLoc,
			null,  // returnLat
			null   // returnLon
		);
	}
}