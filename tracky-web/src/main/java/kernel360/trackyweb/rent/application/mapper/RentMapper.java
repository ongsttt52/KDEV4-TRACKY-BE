package kernel360.trackyweb.rent.application.mapper;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.rent.presentation.dto.RentRequest;

public class RentMapper {

	public static void updateRent(CarEntity car, RentEntity rent, RentRequest rentRequest) {
		rent.update(
			car,
			rentRequest.rentStime(),
			rentRequest.rentEtime(),
			rentRequest.renterName(),
			rentRequest.renterPhone(),
			rentRequest.purpose(),
			rentRequest.rentStatus(),
			rentRequest.rentLoc(),
			rentRequest.returnLoc()
		);
	}
}
