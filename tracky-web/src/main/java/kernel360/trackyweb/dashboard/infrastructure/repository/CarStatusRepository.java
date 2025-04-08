package kernel360.trackyweb.dashboard.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackyweb.dashboard.domain.CarStatus;

public interface CarStatusRepository extends JpaRepository<CarEntity, Long> {

	@Query("SELECT new kernel360.trackyweb.dashboard.domain.CarStatus(c.status, COUNT(c)) FROM CarEntity c GROUP BY c.status")
	List<CarStatus> findAllGroupedByStatus();

}
