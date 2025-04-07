package kernel360.trackyweb.reservation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.DriveEntity;

public interface ReservationDriveRepository extends JpaRepository<DriveEntity, Long> {

}
