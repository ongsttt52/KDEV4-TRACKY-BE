package kernel360.trackyweb.rent.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackycore.core.infrastructure.repository.RentRepository;

@Repository
public interface RentDomainRepository extends RentRepository {

	Optional<RentEntity> findByRentUuid(String rentUuid);

	/** rentUuid로 차량 검색 + 결과값의 앞부분이 검색어와 일치할수록 앞으로 정렬
	 * @param rentUuid
	 * @return 검색 렌트 list
	 */
	@Query("SELECT r FROM RentEntity r " + "WHERE LOWER(r.rentUuid) LIKE LOWER(CONCAT('%', :rentUuid, '%')) "
		+ "ORDER BY LOCATE(LOWER(:rentUuid), LOWER(r.rentUuid)) ASC")
	List<RentEntity> findByRentUuidContainingOrdered(@Param("rentUuid") String rentUuid);

	/**
	 * Uuid가 일치하는 예약건 삭제
	 * @param rentUuid
	 */
	void deleteByRentUuid(String rentUuid);
}
