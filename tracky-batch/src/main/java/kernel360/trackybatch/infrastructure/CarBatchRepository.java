package kernel360.trackybatch.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.infrastructure.repository.CarRepository;

@Repository
public interface CarBatchRepository extends CarRepository {

	@Query("SELECT b.bizUuid, COUNT(c) " +
		"FROM CarEntity c " +
		"JOIN c.biz b " +
		"GROUP BY b.bizUuid")
	List<Object[]> countByBizUuidGrouped();

}
