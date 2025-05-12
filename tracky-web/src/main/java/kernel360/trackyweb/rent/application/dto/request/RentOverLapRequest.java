package kernel360.trackyweb.rent.application.dto.request;

import java.time.LocalDateTime;

public record RentOverLapRequest(
	String mdn,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
}
