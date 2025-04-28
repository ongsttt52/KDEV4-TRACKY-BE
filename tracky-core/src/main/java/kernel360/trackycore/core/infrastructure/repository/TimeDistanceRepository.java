package kernel360.trackycore.core.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.TimeDistanceEntity;

public interface TimeDistanceRepository extends JpaRepository<TimeDistanceEntity, Long> {
}
