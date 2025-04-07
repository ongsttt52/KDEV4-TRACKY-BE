package kernel360.trackycore.core.infrastructure.exception;

public class BizException extends GlobalException {
	public BizException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static BizException notFound() {
		return new BizException(ErrorCode.BIZ_NOT_FOUND);
	}
}
