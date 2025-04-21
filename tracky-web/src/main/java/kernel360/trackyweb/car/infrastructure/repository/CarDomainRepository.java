package kernel360.trackyweb.car.infrastructure.repository;

import kernel360.trackycore.core.infrastructure.repository.CarRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import kernel360.trackycore.core.domain.entity.CarEntity;

public interface CarDomainRepository extends CarRepository, CarDomainRepositoryCustom {

	/**
	 * MDN이 일치하는 차량 삭제
	 *
	 * @param mdn 차량 mdn
	 */
	void deleteByMdn(String mdn);

	/**
	 * 랜트 등록 시, 모든 mdn을 셀렉트 박스로 출력
	 * @return
	 */
	@Query("SELECT c.mdn FROM CarEntity c WHERE c.mdn IS NOT NULL")
	List<String> findAllMdns();

	List<CarEntity> findAll();
}
