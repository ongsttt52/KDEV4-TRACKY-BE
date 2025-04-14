package kernel360.trackyweb.member.infrastructure.exception;

import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackycore.core.infrastructure.exception.GlobalException;

public class MemberException extends GlobalException {

	public MemberException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static MemberException notFound() {
		return new MemberException(ErrorCode.MEMBER_NOT_FOUND);
	}

	public static MemberException wrongPwd() {
		return new MemberException(ErrorCode.MEMBER_WRONG_PWD);
	}

	public static MemberException noJwtToken() {
		return new MemberException(ErrorCode.MEMBER_NO_JWT_TOKEN);
	}

	public static MemberException notJwtValid() {
		return new MemberException(ErrorCode.MEMBER_JWT_NOT_VALID);
	}
}


