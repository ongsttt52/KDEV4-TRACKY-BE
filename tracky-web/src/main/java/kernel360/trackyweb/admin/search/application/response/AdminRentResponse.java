package kernel360.trackyweb.admin.search.application.response;

import java.time.LocalDateTime;
import java.util.List;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public record AdminRentResponse(
	String rent_uuid,
	String mdn,
	String bizName,
	String renterName,
	String renterPhone,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	String rentLoc,
	String returnLoc,
	String purpose,
	RentStatus rentStatus
) {
	public static AdminRentResponse from(RentEntity rent) {
		return new AdminRentResponse(
			rent.getRentUuid(),         // rent_uuid
			rent.getCar().getMdn(),              // mdn
			rent.getCar().getBiz().getBizName(), // BizName
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

	public static List<AdminRentResponse> fromList(List<RentEntity> rents) {
		return rents.stream()
			.map(AdminRentResponse::from)
			.toList();
	}
}
