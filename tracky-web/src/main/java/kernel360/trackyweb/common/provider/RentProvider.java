package kernel360.trackyweb.common.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackyweb.common.entity.RentEntity;
import kernel360.trackyweb.common.repository.RentRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentProvider {

	private final RentRepository rentRepository;

	public RentEntity findByRentUuid(String rentUuid) {
		return rentRepository.findByRentUuid(rentUuid)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.RENT_NOT_FOUND));
	}
}
