package kernel360.trackyweb.car.infrastructure.repository;

import kernel360.trackycore.core.infrastructure.repository.CarRepository;

public interface CarDomainRepository extends CarRepository, CarDomainRepositoryCustom {

	/**
	 * MDN이 일치하는 차량 삭제
	 *
	 * @param mdn 차량 mdn
	 */
	void deleteByMdn(String mdn);

}
