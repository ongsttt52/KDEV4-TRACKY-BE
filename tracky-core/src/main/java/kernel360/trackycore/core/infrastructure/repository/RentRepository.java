package kernel360.trackycore.core.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

	@Query("SELECT r FROM RentEntity r WHERE r.car = :car AND :onTime BETWEEN r.rentStime AND r.rentEtime")
	Optional<RentEntity> findMyCarAndTime(CarEntity car, LocalDateTime onTime);
}
