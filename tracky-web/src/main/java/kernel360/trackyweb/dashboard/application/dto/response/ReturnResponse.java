package kernel360.trackyweb.dashboard.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.enums.CarType;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public record ReturnResponse(
	String rentUuid,
	String renterName,
	RentStatus rentStatus,
	LocalDateTime rentEtime,
	String mdn,
	String carPlate,
	CarType carType
) {
}
