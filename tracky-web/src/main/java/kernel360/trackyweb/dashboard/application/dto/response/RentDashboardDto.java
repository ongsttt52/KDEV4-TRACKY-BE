package kernel360.trackyweb.dashboard.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.enums.CarType;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public record RentDashboardDto(
	String rentUuid,
	String renterName,
	String mdn,
	String carPlate,
	CarType carType,
	RentStatus rentStatus,
	LocalDateTime rentStime,
	LocalDateTime rentEtime
) {
	public static RentDashboardDto from(RentEntity rent) {
		return new RentDashboardDto(
			rent.getRentUuid(),
			rent.getRenterName(),
			rent.getCar().getMdn(),
			rent.getCar().getCarPlate(),
			rent.getCar().getCarType(),
			rent.getRentStatus(),
			rent.getRentStime(),
			rent.getRentEtime()
		);
	}
}
