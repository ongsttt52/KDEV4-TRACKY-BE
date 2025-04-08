package kernel360.trackyweb.reservation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.RentEntity;

@Repository
public interface ReservationRentRepository extends JpaRepository<RentEntity, Long> {
	RentEntity findByRentUuid(String rentUuid);
}
