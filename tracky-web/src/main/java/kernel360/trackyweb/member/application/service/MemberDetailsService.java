package kernel360.trackyweb.member.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kernel360.trackyweb.member.infrastructure.entity.Member;
import kernel360.trackyweb.member.domain.model.MemberPrincipal;
import kernel360.trackyweb.member.infrastructure.repository.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	public MemberDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니당."));
		return new MemberPrincipal(member);
	}

}
