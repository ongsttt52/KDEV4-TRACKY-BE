package kernel360.trackyweb.car.application.dto.request;

import org.springframework.data.domain.Pageable;

public record CarSearchByFilterRequest(
	String search,
	String status,
	Pageable pageable
) {
}
