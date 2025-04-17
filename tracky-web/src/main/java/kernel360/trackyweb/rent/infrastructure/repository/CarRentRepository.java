package kernel360.trackyweb.rent.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;

@Repository
public interface CarRentRepository extends JpaRepository<CarEntity, Long> {

	@Query("SELECT c.mdn FROM CarEntity c WHERE c.mdn IS NOT NULL")
	List<String> findAllMdns();
}
