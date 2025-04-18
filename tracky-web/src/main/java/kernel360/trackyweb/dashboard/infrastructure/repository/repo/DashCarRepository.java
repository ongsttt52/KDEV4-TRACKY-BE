package kernel360.trackyweb.dashboard.infrastructure.repository.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.CarEntity;

public interface DashCarRepository extends JpaRepository<CarEntity, Long> {
	Optional<CarEntity> findByMdn(String mdn);
}
