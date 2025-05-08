package kernel360.trackyweb.statistic.application.dto.response;

import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarStatisticResponse(
	String mdn,
	String carPlate,
	CarType carType,
	int driveSec,
	double driveDistance,
	int avgSpeed
) {
}
