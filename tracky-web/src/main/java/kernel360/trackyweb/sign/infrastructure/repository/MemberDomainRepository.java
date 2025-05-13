package kernel360.trackyweb.sign.infrastructure.repository;

import java.util.List;

import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackycore.core.domain.entity.enums.MemberStatus;
import kernel360.trackycore.core.infrastructure.repository.MemberRepository;

public interface MemberDomainRepository extends MemberRepository, MemberDomainRepositoryCustom {

	boolean existsByMemberId(String memberId);

	List<MemberEntity> findByStatus(MemberStatus status);

}
