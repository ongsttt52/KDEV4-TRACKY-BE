package kernel360.trackyweb.rent.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.infrastructure.repository.RentRepository;

@Repository
public interface RentDomainRepository extends RentRepository, RentRepositoryCustom {

	Optional<RentEntity> findByRentUuid(String rentUuid);


	@Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, r.rentStime, r.rentEtime)) FROM RentEntity r WHERE r.rentStime IS NOT NULL AND r.rentEtime IS NOT NULL")
	Long getTotalRentDurationInMinutes();

	/**
	 * Uuid가 일치하는 예약건 삭제
	 * @param rentUuid
	 */
	void deleteByRentUuid(String rentUuid);
}
