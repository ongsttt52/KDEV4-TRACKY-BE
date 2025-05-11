package kernel360.trackyweb.rent.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackyweb.admin.search.application.request.AdminRentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.response.RentMdnResponse;

public interface RentRepositoryCustom {
	Page<RentEntity> searchRentByFilterAdmin(AdminRentSearchByFilterRequest request);

	Page<RentEntity> searchRentByFilter(RentSearchByFilterRequest request, String bizUuid);

	List<RentMdnResponse> findRentableMdn(Long bizId);

	List<RentEntity> findDelayedRents(String bizUuid, LocalDateTime now);

	List<RentEntity> findOverlappingRent(String mdn, LocalDateTime start, LocalDateTime end);

}
