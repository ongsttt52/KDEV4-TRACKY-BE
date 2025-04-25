package kernel360.trackyweb.realtime.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record RealTimeCarListRequest(
	String search,
	Integer page,
	Integer size
) {
	public Pageable toPageable() {
		int safePage = page != null ? page : 0;
		int safeSize = size != null ? size : 20;
		return PageRequest.of(safePage, safeSize);
	}
}
