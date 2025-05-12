package kernel360.trackyweb.dashboard.application.dto.response;

import kernel360.trackycore.core.domain.entity.enums.CarStatus;

public record DashboardCarStatusResponse(
	CarStatus carStatus,
	long carCount
) {
}
