package kernel360.trackyweb.rent.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.RentEntity;

public record OverlappingRentResponse(
	LocalDateTime rentStime,
	LocalDateTime rentEtime
) {
	public static OverlappingRentResponse from(RentEntity entity) {
		return new OverlappingRentResponse(
			entity.getRentStime(),
			entity.getRentEtime()
		);
	}
}
