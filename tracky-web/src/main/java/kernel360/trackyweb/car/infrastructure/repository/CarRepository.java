package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

	@Query("SELECT c FROM CarEntity c " + "WHERE LOWER(c.mdn) LIKE LOWER(CONCAT('%', :keyword, '%')) "
		+ "ORDER BY LOCATE(LOWER(:keyword), LOWER(c.mdn)) ASC")
	List<CarEntity> findByMdnContainingOrdered(@Param("keyword") String keyword);
}
