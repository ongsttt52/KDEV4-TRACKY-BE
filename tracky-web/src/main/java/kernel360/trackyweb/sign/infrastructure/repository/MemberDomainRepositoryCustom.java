package kernel360.trackyweb.sign.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.MemberEntity;

public interface MemberDomainRepositoryCustom {
	Page<MemberEntity> findByBizNameOrAdmin(String search, Pageable pageable);
}
