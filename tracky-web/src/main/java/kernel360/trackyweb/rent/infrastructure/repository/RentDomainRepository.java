package kernel360.trackyweb.rent.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.infrastructure.repository.RentRepository;

@Repository
public interface RentDomainRepository extends RentRepository, RentRepositoryCustom {

	Optional<RentEntity> findByRentUuid(String rentUuid);

	/**
	 * Uuid가 일치하는 예약건 삭제
	 * @param rentUuid
	 */
	void deleteByRentUuid(String rentUuid);
}
