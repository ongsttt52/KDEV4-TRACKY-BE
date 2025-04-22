package kernel360.trackyweb.reservation.infrastructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.domain.entity.CarEntity;

@Repository
public interface ReservationCarRepository extends JpaRepository<CarEntity, Long> {

}
