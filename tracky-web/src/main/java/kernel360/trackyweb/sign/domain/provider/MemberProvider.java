package kernel360.trackyweb.sign.domain.provider;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackycore.core.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberProvider {

	private final MemberRepository memberRepository;

	public MemberEntity getMember(String memberId) {
		return memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.MEMBER_NOT_FOUND));
	}

	public void save(MemberEntity member) {
		memberRepository.save(member);
	}
}
