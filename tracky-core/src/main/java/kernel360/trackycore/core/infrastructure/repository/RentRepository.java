package kernel360.trackycore.core.infrastructure.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kernel360.trackycore.core.common.entity.RentEntity;

public interface RentRepository extends JpaRepository<RentEntity, Long> {
	Optional<RentEntity> findByRentUuid(String rentUuid);
}
