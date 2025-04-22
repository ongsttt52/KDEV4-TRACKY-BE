package kernel360.trackyweb.drive.application.dto.request;

import org.springframework.data.domain.Pageable;

public record DriveSearchFilterRequest(
	String search,
	Pageable pageable
) {

}
