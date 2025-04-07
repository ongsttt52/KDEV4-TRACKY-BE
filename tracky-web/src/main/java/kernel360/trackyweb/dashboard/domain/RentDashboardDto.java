package kernel360.trackyweb.dashboard.domain;

import java.time.LocalDateTime;

public record RentDashboardDto (
	String  rentUuid,
	String renterName,
	String mdn,
	String carPlate,
	String carType,
	String rentStatus,
    LocalDateTime rentStime,
	LocalDateTime rentEtime
){ }
