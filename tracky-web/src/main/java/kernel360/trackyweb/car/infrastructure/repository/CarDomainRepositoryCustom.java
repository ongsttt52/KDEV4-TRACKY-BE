package kernel360.trackyweb.car.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.CarEntity;

public interface CarDomainRepositoryCustom {
	/**
	 * 랜트 등록 시, 해당 업체에 맞는 모든 mdn 반환
	 **/
	List<String> findAllMdnByBizId(String bizUuid);

	/**
	 * 필터 조건 기반 검색
	 */
	Page<CarEntity> searchCarByFilter(
		String search,
		String status,
		String carType,
		Pageable pageable);

	Page<CarEntity> searchDriveCarByFilter(
		String bizUuid,
		String search,
		Pageable pageable);
}
