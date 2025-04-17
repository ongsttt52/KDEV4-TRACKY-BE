package kernel360.trackycore.core.common.exception;

/**
 * 공통 Exception
 */
public class GlobalException extends RuntimeException {

	private final ErrorCode errorCode;

	protected GlobalException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public static GlobalException throwError(ErrorCode errorCode) {
		throw new GlobalException(errorCode);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
