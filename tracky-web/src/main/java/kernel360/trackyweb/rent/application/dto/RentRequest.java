package kernel360.trackyweb.rent.application.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.RentEntity;

public record RentRequest(
	String mdn,
	String renterName,
	String renterPhone,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	String rentLoc,
	String rentLat,
	String rentLon,
	String returnLoc,
	String returnLat,
	String returnLon,
	String purpose
)  {
	public RentEntity toEntity(String rentUuid, String rentStatus) {
		return new RentEntity(
			mdn,
			rentUuid,
			rentStime,
			rentEtime,
			renterName,
			renterPhone,
			purpose,
			rentStatus,
			rentLoc,
			rentLat,
			rentLon,
			returnLoc,
			returnLat,
			returnLon
		);
	}
}