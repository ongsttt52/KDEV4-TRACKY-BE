package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;

@Repository
public interface DriveEntityRepository extends JpaRepository<DriveEntity, Long> {

	@Query("SELECT d FROM DriveEntity d WHERE d.car = :car " +
		"AND d.driveOnTime <= :otime " +
		"ORDER BY d.id DESC LIMIT 1")
	DriveEntity findByCarAndOtime(CarEntity car, LocalDateTime otime);
}
