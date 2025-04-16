package kernel360.trackyweb.car.application.dto.request;

import org.springframework.data.domain.Pageable;

public record CarSearchByFilterRequest(
	String mdn,
	String status,
	String purpose,
	Pageable pageable
) {
}
