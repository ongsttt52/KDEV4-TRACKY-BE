package kernel360.trackyweb.reservation.infrastructure.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.DriveEntity;

@Repository
public interface ReservationDriveRepository extends JpaRepository<DriveEntity, Long> {
	List<DriveEntity> findAllByRent_RentUuid(String uuid);
}
