package kernel360.trackycore.core.common.exception;

/**
 * 공통 Exception
 */
public class GlobalException extends RuntimeException {

	private final ErrorCode errorCode;
	private final Object data;

	protected GlobalException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.data = null;
	}

	protected GlobalException(ErrorCode errorCode, Object data) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.data = data;
	}

	public static GlobalException throwError(ErrorCode errorCode) {
		throw new GlobalException(errorCode);
	}

	public static GlobalException throwError(ErrorCode errorCode, Object data) {
		throw new GlobalException(errorCode, data);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public Object getData() {
		return data;
	}
}
