package kernel360.trackyweb.rent.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {
	Optional<RentEntity> searchByRentUuid(String rentUuid);
	Optional<RentEntity> findByRentUuid(String rentUuid);
	void deleteByRentUuid(String rentUuid);
	Optional<RentEntity> findDetailByRentUuid(String rentUuid);

	@Query("SELECT r FROM RentEntity r " + "WHERE LOWER(r.rentUuid) LIKE LOWER(CONCAT('%', :rentUuid, '%')) "
		+ "ORDER BY LOCATE(LOWER(:rentUuid), LOWER(r.rentUuid)) ASC")
	List<RentEntity> findByRentUuidContainingOrdered(@Param("rentUuid") String rentUuid);

}
