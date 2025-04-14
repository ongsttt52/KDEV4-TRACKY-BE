package kernel360.trackyweb.member.infrastructure.exception;

import kernel360.trackycore.core.infrastructure.exception.ErrorCode;
import kernel360.trackycore.core.infrastructure.exception.GlobalException;

public class MemberException extends GlobalException {

	public MemberException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static MemberException sendError(ErrorCode errorCode) {
		return new MemberException(errorCode);
	}
}


