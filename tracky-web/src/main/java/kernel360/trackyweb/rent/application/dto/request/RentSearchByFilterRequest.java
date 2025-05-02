package kernel360.trackyweb.rent.application.dto.request;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public record RentSearchByFilterRequest(
	String rentUuid,
	String search,
	RentStatus status,
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate rentDate,
	Integer page,
	Integer size
) {
	public Pageable toPageable() {
		int safePage = page != null ? page : 0;
		int safeSize = size != null ? size : 10;
		return PageRequest.of(safePage, safeSize);
	}
}
