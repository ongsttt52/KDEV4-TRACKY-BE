package kernel360.trackyweb.rent.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;

public interface RentRepositoryCustom {
	Page<RentEntity> searchRentByFilter(RentSearchByFilterRequest request, String bizUuid);

	List<RentEntity> findDelayedRents(String bizUuid, LocalDateTime now);

}
