package kernel360trackybe.trackybatch.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kernel360.trackycore.core.infrastructure.repository.DriveRepository;

public interface DriveBatchRepository extends DriveRepository {

	@Query("SELECT c.biz.bizUuid, COUNT(DISTINCT d.car.mdn) " +
		"FROM DriveEntity d " +
		"JOIN CarEntity c ON d.car.mdn = c.mdn " +
		"WHERE (d.driveOffTime IS NULL OR (d.driveOffTime >= :startDate AND d.driveOffTime < :endDate)) " +
		"GROUP BY c.biz.bizUuid")
	List<Object[]> countDailyOperatingCarsGroupedByBizUuid(
		@Param("startDate") LocalDateTime startDate,
		@Param("endDate") LocalDateTime endDate
	);

}
