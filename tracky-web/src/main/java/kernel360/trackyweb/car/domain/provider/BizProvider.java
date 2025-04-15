package kernel360.trackyweb.car.domain.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.infrastructure.exception.BizException;
import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackyweb.car.infrastructure.repo.CarBizRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BizProvider {

	private final CarBizRepository carBizRepository;

	public BizEntity getBiz(Long id) {
		return carBizRepository.findById(id)
			.orElseThrow(() -> BizException.sendError(ErrorCode.BIZ_NOT_FOUND));
	}

}
