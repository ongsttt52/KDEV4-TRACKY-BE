package kernel360.trackyweb.sign.application.validation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackyweb.biz.domain.provider.BizDomainProvider;
import kernel360.trackyweb.sign.domain.provider.MemberProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignValidator {

	private final PasswordEncoder passwordEncoder;
	private final MemberProvider memberProvider;
	private final BizDomainProvider bizDomainProvider;

	public void validatePassword(String pwd, MemberEntity member) {
		if (!passwordEncoder.matches(pwd, member.getPwd())) {
			throw GlobalException.throwError(ErrorCode.MEMBER_WRONG_PWD);
		}
	}

	public void validateSignup(String memberId, String bizRegNum) {
		if (memberProvider.existsByMemberId(memberId)) {
			throw GlobalException.throwError(ErrorCode.ALREADY_MEMBER_ID);
		}

		if (bizDomainProvider.existsByBizRegNum(bizRegNum)) {
			throw GlobalException.throwError(ErrorCode.ALREADY_BIZ_REG_NUM);
		}
	}

}
