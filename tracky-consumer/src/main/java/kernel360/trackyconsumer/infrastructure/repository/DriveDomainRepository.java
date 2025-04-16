package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.infrastructure.repository.DriveRepository;

public interface DriveDomainRepository extends DriveRepository {

	@Query("SELECT d FROM DriveEntity d WHERE d.car = :car " +
		"AND d.driveOnTime <= :otime " +
		"ORDER BY d.id DESC LIMIT 1")
	Optional<DriveEntity> findByCarAndOtime(CarEntity car, LocalDateTime otime);
}
