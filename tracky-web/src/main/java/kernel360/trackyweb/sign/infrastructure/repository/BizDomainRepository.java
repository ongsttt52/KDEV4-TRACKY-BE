package kernel360.trackyweb.sign.infrastructure.repository;

import kernel360.trackycore.core.infrastructure.repository.BizRepository;

public interface BizDomainRepository extends BizRepository {

	boolean existsByBizRegNum(String bizRegNum);
	
}
