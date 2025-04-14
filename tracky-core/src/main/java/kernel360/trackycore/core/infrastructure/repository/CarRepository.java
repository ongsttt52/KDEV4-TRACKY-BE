package kernel360.trackyweb.car.infrastructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long>, CarRepositoryCustom {

}
