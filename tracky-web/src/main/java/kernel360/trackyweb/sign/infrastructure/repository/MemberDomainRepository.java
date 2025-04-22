package kernel360.trackyweb.sign.infrastructure.repository;

import kernel360.trackycore.core.infrastructure.repository.MemberRepository;

public interface MemberDomainRepository extends MemberRepository {

	boolean existsByMemberId(String memberId);
	
}
