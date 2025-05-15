package kernel360.trackyweb.car.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarSearchByFilterRequest(
	String search,
	String status,
	String carType,
	Integer page,
	Integer size
) {
	public Pageable toPageable() {
		int safePage = page != null ? page : 0;
		int safeSize = size != null ? size : 10;
		return PageRequest.of(safePage, safeSize);
	}

	public CarStatus toCarStatus() {
		return CarStatus.from(status);
	}

	public CarType toCarType() {
		return CarType.from(carType);
	}
}
