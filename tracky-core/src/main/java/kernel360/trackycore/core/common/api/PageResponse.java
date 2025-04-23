package kernel360.trackycore.core.common.api;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse {
	private long totalElements;

	private int totalPages;

	private int number;

	private int size;

	public static PageResponse from(Page page) {
		return new PageResponse(
			page.getTotalElements(),
			page.getTotalPages(),
			page.getNumber(),
			page.getSize()
		);
	}
}
