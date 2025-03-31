package kernel360.trackyweb.member.application.service;

import kernel360.trackyweb.member.infrastructure.entity.Member;
import kernel360.trackyweb.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 주어진 memberId와 평문 비밀번호를 통해 회원 인증 처리.
	 * 인증 실패 시 IllegalArgumentException을 던짐.
	 */
	public Member authenticate(String memberId, String pwd) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		if (!passwordEncoder.matches(pwd, member.getPwd())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		return member;
	}
}