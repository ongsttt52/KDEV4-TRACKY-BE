package kernel360.trackyweb.admin.search.application.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record AdminCarSearchByFilterRequest(
	String bizSearch,
	String search,
	CarStatus status,
	CarType carType,
	Integer page,
	Integer size
) {
	public Pageable toPageable() {
		int safePage = page != null ? page : 0;
		int safeSize = size != null ? size : 10;
		return PageRequest.of(safePage, safeSize);
	}
}
