package kernel360.trackycore.core.common.provider;

import org.springframework.stereotype.Component;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.infrastructure.repository.RentRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentProvider {

	private final RentRepository rentRepository;

	public RentEntity getRent(String rentUuid) {
		return rentRepository.findByRentUuid(rentUuid)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.RENT_NOT_FOUND));
	}
}
