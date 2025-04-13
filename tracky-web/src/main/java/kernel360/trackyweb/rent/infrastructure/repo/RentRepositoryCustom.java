package kernel360.trackyweb.rent.infrastructure.repo;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.common.entity.RentEntity;

public interface RentRepositoryCustom {
	Page<RentEntity> searchByFilters(String rentUuid, String rentStatus, LocalDateTime rentDate, Pageable pageable);
}

