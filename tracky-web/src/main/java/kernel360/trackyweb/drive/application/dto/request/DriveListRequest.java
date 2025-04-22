package kernel360.trackyweb.drive.application.dto.request;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record DriveListRequest(
	String mdn,
	LocalDateTime startDateTime,
	LocalDateTime endDateTime,
	Integer page,
	Integer size
) {
	public Pageable toPageable() {
		int safePage = page != null ? page : 0;
		int safeSize = size != null ? size : 20;
		return PageRequest.of(safePage, safeSize);
	}

}
