package kernel360.trackyweb.reservation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.RentEntity;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

	RentEntity findByUuid(String uuid);
}
