package kernel360.trackyweb.reservation.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.DriveEntity;

public interface DriveRepository extends JpaRepository<DriveEntity, Long> {

}
