package kernel360.trackyweb.rent.domain.provider;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.querydsl.core.Tuple;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.QRentEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.response.RentMdnResponse;
import kernel360.trackyweb.rent.infrastructure.repository.RentDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentDomainProvider {

	private final RentDomainRepository rentDomainRepository;

	public RentEntity save(RentEntity rent) {
		return rentDomainRepository.save(rent);
	}

	public Page<RentEntity> searchRentByFilter(RentSearchByFilterRequest request, String bizUuid) {
		return rentDomainRepository.searchRentByFilter(request, bizUuid);
	}

	public List<RentMdnResponse> getRentableMdnList(String bizUuid) {
		List<Tuple> tuples = rentDomainRepository.findRentableMdn(bizUuid);
		return tuples.stream().map(tuple -> {
			return new RentMdnResponse(tuple.get(QRentEntity.rentEntity.car.mdn),
				tuple.get(QRentEntity.rentEntity.car.status));
		}).toList();
	}

	public List<RentEntity> getDelayedRentList(String bizUuid, LocalDateTime now) {
		return rentDomainRepository.findDelayedRents(bizUuid, now);
	}

	public Long getTotalRentDurationInMinutes() {
		return rentDomainRepository.getTotalRentDurationInMinutes();
	}

	public void softDelete(String rentUuid) {
		RentEntity rent = rentDomainRepository.findByRentUuid(rentUuid)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.RENT_NOT_FOUND));
		rent.updateStatus(RentStatus.DELETED);
	}

	public Long count() {
		return rentDomainRepository.count();
	}

}
