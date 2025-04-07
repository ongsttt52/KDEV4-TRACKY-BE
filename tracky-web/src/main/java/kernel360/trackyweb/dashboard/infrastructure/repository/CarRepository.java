package kernel360.trackyweb.dashboard.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.common.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, String> {
}
