package kernel360.trackyweb.biz.domain.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackyweb.biz.infrastructure.repository.BizDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BizDomainProvider {

	private final BizDomainRepository bizDomainRepository;

	public void save(BizEntity biz) {
		bizDomainRepository.save(biz);
	}

	public boolean existsByBizRegNum(String bizRegNum) {
		return bizDomainRepository.existsByBizRegNum(bizRegNum);
	}
}
