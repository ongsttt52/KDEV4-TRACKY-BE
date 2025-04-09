package kernel360.trackyweb.rent.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;

@Repository("carRepositoryForRent")
public interface CarRepository extends JpaRepository<CarEntity, Long> {
	Optional<CarEntity> findByMdn(String mdn);

	@Query("SELECT c.mdn FROM CarEntity c WHERE c.mdn IS NOT NULL")
	List<String> findAllMdns();
}
