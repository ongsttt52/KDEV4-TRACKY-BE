package kernel360.trackyweb.rent.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.infrastructure.entity.RentEntity;

public interface RentRepository extends JpaRepository<RentEntity, Long> {
}
