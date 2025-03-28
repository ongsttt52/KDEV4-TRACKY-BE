package kernel360.trackycore.core.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.entity.GpsHistoryEntity;

@Repository
public interface GpsHistoryEntityRepository extends JpaRepository<GpsHistoryEntity, Long> {
}
