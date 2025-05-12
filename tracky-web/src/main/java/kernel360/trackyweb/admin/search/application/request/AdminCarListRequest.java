package kernel360.trackyweb.admin.search.application.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record AdminCarListRequest (
	String bizSearch,
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

