package kernel360.trackyemulator.infrastructure.exception;

import lombok.Getter;

@Getter
public class EmulatorException extends RuntimeException {

	private final ErrorCode errorCode;

	public EmulatorException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public static EmulatorException sendError(ErrorCode errorCode) {
		return new EmulatorException(errorCode);
	}
}
