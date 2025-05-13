package kernel360.trackyweb.sign.domain.provider;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackycore.core.domain.entity.enums.MemberStatus;
import kernel360.trackycore.core.infrastructure.repository.MemberRepository;
import kernel360.trackyweb.sign.infrastructure.repository.MemberDomainRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberProvider {

	private final MemberRepository memberRepository;
	private final MemberDomainRepository memberDomainRepository;

	public MemberEntity getMember(String memberId) {
		return memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.MEMBER_NOT_FOUND));
	}

	public void save(MemberEntity member) {
		memberRepository.save(member);
	}
	
	public boolean existsByMemberId(String memberId) {
		return memberDomainRepository.existsByMemberId(memberId);
	}

	public List<MemberEntity> findByStatus(MemberStatus status) {
		return memberDomainRepository.findByStatus(status);
	}

	public Page<MemberEntity> getMembersBySearchFilter(String search, Pageable pageable) {
		return memberDomainRepository.findByBizNameOrAdmin(search, pageable);
	}

}
