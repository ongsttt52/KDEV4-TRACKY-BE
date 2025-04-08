package kernel360.trackyweb.rent.presentation.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.car.infrastructure.repository.CarRepository;

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
	public RentEntity toEntity(CarEntity car, String rentUuid, String rentStatus) {

		return RentEntity.create(
			car,
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