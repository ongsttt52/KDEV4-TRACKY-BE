package kernel360.trackyweb.rent.domain.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackyweb.car.infrastructure.repository.CarDomainRepository;
import kernel360.trackyweb.rent.infrastructure.repository.RentDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentDomainProvider {

	private final RentDomainRepository rentDomainRepository;
	private final CarDomainRepository carDomainRepository;

	public RentEntity save(RentEntity rent) {
		return rentDomainRepository.save(rent);
	}

	public void delete(String rentUuid) {
		rentDomainRepository.deleteByRentUuid(rentUuid);
	}

	public List<String> getAllMdnByBizId(Long bizId) {
		return carDomainRepository.findAllMdnByBizId(bizId);
	}

}
