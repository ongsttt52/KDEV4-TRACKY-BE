package kernel360.trackyweb.notice.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record NoticeSearchByFilterRequest(
	String search,
	Boolean isImportant,
	Integer page,
	Integer size
) {

	public Pageable toPageable() {
		int safePage = page != null ? page : 0;
		int safeSize = size != null ? size : 20;
		return PageRequest.of(safePage, safeSize);
	}
}
