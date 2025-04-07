package kernel360.trackyweb.reservation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.RentEntity;

public interface ReservationRentRepository extends JpaRepository<RentEntity, Long> {

	RentEntity findByRentUuid(String rentUuid);
}
