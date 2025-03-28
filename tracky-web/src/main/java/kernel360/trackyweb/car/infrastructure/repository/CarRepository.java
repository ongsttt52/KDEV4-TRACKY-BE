package kernel360.trackyweb.car.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
}
