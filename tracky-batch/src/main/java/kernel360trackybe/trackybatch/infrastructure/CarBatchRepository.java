package kernel360trackybe.trackybatch.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.infrastructure.repository.CarRepository;

public interface CarBatchRepository extends CarRepository {

	@Query("SELECT b.bizUuid, COUNT(c) " +
		"FROM CarEntity c " +
		"JOIN c.biz b " +
		"GROUP BY b.bizUuid")
	List<Object[]> countByBizUuidGrouped();

}
