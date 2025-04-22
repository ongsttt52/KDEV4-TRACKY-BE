package kernel360.trackyweb.sign.application;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackyweb.sign.application.dto.request.SignupRequest;
import kernel360.trackyweb.sign.application.validation.MemberValidator;
import kernel360.trackyweb.sign.domain.provider.BizDomainProvider;
import kernel360.trackyweb.sign.domain.provider.MemberProvider;
import kernel360.trackyweb.sign.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {

	private final MemberProvider memberProvider;
	private final BizDomainProvider bizDomainProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberValidator memberValidator;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupRequest signupRequest) {
		BizEntity biz = BizEntity.create(
			signupRequest.bizName(),
			UUID.randomUUID().toString(),
			signupRequest.bizRegNum(),
			signupRequest.bizAdmin(),
			signupRequest.bizPhoneNum(),
			null
		);
		bizDomainProvider.save(biz);

		MemberEntity member = MemberEntity.create(
			biz,
			signupRequest.memberId(),
			passwordEncoder.encode(signupRequest.pwd()),
			signupRequest.email(),
			"USER",
			"WAIT",
			null
		);
		memberProvider.save(member);
	}

	/**
	 * 주어진 memberId와 평문 비밀번호를 통해 회원 인증 처리.
	 */
	public MemberEntity authenticate(String memberId, String pwd) {

		log.info("Login attempt for memberId: {}", memberId);

		MemberEntity member = memberProvider.getMember(memberId);

		memberValidator.validatePassword(pwd, member);

		return member;
	}

	public String generateJwtToken(MemberEntity member) {
		String bizName = member.getBizId().getBizName();
		Long bizId = member.getBizId().getId();
		String bizUuid = member.getBizId().getBizUuid();

		return jwtTokenProvider.generateToken(member.getMemberId(), member.getRole(), bizName, bizId, bizUuid);
	}
}
