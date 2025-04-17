package kernel360.trackyweb.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackyweb.common.entity.RentEntity;

public interface RentRepository extends JpaRepository<RentEntity, Long> {
	Optional<RentEntity> findByRentUuid(String rentUuid);
}
