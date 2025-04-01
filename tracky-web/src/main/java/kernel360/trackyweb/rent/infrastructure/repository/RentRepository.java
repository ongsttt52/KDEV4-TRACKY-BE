package kernel360.trackyweb.rent.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.RentEntity;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {
}
