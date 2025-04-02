package kernel360trackybe.trackyhub.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

	@Query("SELECT c.mdn FROM CarEntity c")
	List<String> findAllMdn();
}
