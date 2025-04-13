package kernel360.trackyweb.car.infrastructure.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.common.entity.CarEntity;

public interface CarRepositoryCustom {
	Page<CarEntity> searchByFilter(String mdn, String status, String purpose, Pageable pageable);
}
