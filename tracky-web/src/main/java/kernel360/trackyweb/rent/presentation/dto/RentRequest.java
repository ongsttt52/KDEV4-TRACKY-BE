package kernel360.trackyweb.rent.presentation.dto;

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
	String purpose,
	String rentStatus
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
			0,  // rentLat
			0,  // rentLon
			returnLoc,
			0,  // returnLat
			0   // returnLon
		);
	}
}