package kernel360.trackyconsumer.infrastructure.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;

@Repository
public interface RentEntityRepository extends JpaRepository<RentEntity, Long> {

	@Query("SELECT r FROM RentEntity r WHERE r.car = :car AND :onTime BETWEEN r.rentStime AND r.rentEtime")
	RentEntity findMyCarAndTime(CarEntity car, LocalDateTime onTime);
}
