package kernel360.trackyweb.rent.domain.provider;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackyweb.car.infrastructure.repository.CarDomainRepository;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;
import kernel360.trackyweb.rent.infrastructure.repository.RentDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentDomainProvider {

	private final RentDomainRepository rentDomainRepository;
	private final CarDomainRepository carDomainRepository;

	public Page<RentEntity> searchRentByFilter(RentSearchByFilterRequest request, String bizUuid) {
		return rentDomainRepository.searchRentByFilter(request, bizUuid);
	}

	public RentEntity save(RentEntity rent) {
		return rentDomainRepository.save(rent);
	}

	// public void delete(String rentUuid) { rentDomainRepository.deleteByRentUuid(rentUuid); }
	public void softDelete(String rentUuid) {
		RentEntity rent = rentDomainRepository.findByRentUuid(rentUuid)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.RENT_NOT_FOUND));
		rent.updateStatus("DELETED");
	}

	public List<String> getAllMdnByBizId(String bizUuid) {
		return carDomainRepository.findAllMdnByBizId(bizUuid);
	}

	public List<RentEntity> findDelayedRentList(String bizUuid, LocalDateTime now) {
		return rentDomainRepository.findDelayedRents(bizUuid, now);
	}

}
