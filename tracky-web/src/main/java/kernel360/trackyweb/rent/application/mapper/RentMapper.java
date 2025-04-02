package kernel360.trackyweb.rent.application.mapper;

import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.rent.presentation.dto.RentRequest;

public class RentMapper {

	public static void updateRent(RentEntity rent, RentRequest rentRequest) {
		rent.update(
			rentRequest.mdn(),
			rentRequest.rentStime(),
			rentRequest.rentEtime(),
			rentRequest.renterName(),
			rentRequest.renterPhone(),
			rentRequest.purpose(),
			rentRequest.rentLoc(),
			rentRequest.returnLoc()
		);
	}
}
