package kernel360.trackyweb.member.application.validation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackyweb.member.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final PasswordEncoder passwordEncoder;

	public void validatePassword(String pwd, MemberEntity member) {
		if (!passwordEncoder.matches(pwd, member.getPwd())) {
			throw GlobalException.throwError(ErrorCode.MEMBER_WRONG_PWD);
		}
	}

}
