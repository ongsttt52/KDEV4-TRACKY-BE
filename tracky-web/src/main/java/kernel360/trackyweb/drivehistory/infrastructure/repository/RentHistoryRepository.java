package kernel360.trackyweb.drivehistory.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistory;

@Repository
public interface RentHistoryRepository extends JpaRepository<RentEntity, Long> {

	@Query("""
		    SELECT new kernel360.trackyweb.drivehistory.domain.RentDriveHistory(
		        r.rentUuid,
		        r.renterName,
		        c.mdn,
		        r.rentStime,
		        r.rentEtime
		    )
		    FROM RentEntity r
		    JOIN r.car c
		    ORDER BY r.rentStime DESC
		""")
	List<RentDriveHistory> findAllRentHistories();

}
