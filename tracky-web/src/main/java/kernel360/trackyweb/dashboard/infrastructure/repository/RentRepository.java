package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.RentEntity;

public interface RentRepository extends JpaRepository<RentEntity, String > {

	List<RentEntity> findByRentStimeBetween(LocalDateTime start, LocalDateTime end);
}
