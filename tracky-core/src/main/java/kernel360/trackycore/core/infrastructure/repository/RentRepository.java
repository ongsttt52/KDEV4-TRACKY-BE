package kernel360.trackycore.core.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;

public interface RentRepository extends JpaRepository<RentEntity, Long> {
	Optional<RentEntity> findByRentUuid(String rentUuid);

	List<RentEntity> findAllByRentStatus(RentStatus rentStatus);
}
