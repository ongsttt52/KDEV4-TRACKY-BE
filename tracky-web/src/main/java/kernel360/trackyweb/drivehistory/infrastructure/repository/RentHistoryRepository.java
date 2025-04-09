package kernel360.trackyweb.drivehistory.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistoryDto;

public interface RentHistoryRepository extends Repository<RentEntity, Long> {

	@Query("""
		    SELECT new kernel360.trackyweb.drivehistory.domain.RentDriveHistoryDto(
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
	List<RentDriveHistoryDto> findAllRentHistories();
}
