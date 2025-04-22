package kernel360.trackyconsumer.rent.domain.provider;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kernel360.trackyconsumer.rent.infrastructure.repository.RentDomainRepository;
import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentDomainProvider {

	private final RentDomainRepository rentDomainRepository;

	public RentEntity getRent(CarEntity car, LocalDateTime carOnTime) {
		return rentDomainRepository.getRent(car, carOnTime)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.RENT_NOT_FOUND));
	}
}
