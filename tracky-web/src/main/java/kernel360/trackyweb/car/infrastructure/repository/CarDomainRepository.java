package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.infrastructure.repository.CarRepository;
import kernel360.trackyweb.dashboard.domain.CarStatus;

public interface CarDomainRepository extends CarRepository, CarDomainRepositoryCustom {

	/**
	 * MDN이 일치하는 차량 삭제
	 *
	 * @param mdn 차량 mdn
	 */
	void deleteByMdn(String mdn);

	@Query("SELECT new kernel360.trackyweb.dashboard.domain.CarStatus(c.status, COUNT(c)) FROM CarEntity c GROUP BY c.status")
	List<CarStatus> findAllGroupedByStatus();

}
