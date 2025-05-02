package kernel360.trackyweb.rent.application.dto.response;

import kernel360.trackycore.core.domain.entity.enums.CarStatus;

public record RentMdnResponse(
	String mdn,
	CarStatus status
) {
}
