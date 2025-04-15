package kernel360.trackycore.core.common.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.infrastructure.exception.BizException;
import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackycore.core.infrastructure.repository.BizRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BizProvider {

	private final BizRepository bizRepository;

	public BizEntity getBiz(Long id) {
		return bizRepository.findById(id)
			.orElseThrow(() -> BizException.sendError(ErrorCode.BIZ_NOT_FOUND));
	}
}
