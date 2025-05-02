package kernel360.trackycore.core.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel360.trackycore.core.domain.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

	/**
	 * mdn 차량이 존재하는지 체크
	 * @param mdn 차량 mdn
	 * @return boolean
	 */
	boolean existsByMdn(String mdn);

	/**
	 * mdn이 일치하는 차량 찾기
	 * @param mdn 차량 mdn
	 * @return 차량 단건 조회
	 */
	Optional<CarEntity> findByMdn(String mdn);

	/**
	 * 업체 uuid에 해당하는 차량 리스트 조회
	 * @param bizUuid 업체 uuid
	 * @return 차량 리스트 조회
	 */
	List<String> findByBizUuid(String bizUuid);
}
