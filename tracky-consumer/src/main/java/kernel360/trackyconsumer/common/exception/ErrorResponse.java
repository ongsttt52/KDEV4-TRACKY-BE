package kernel360.trackyconsumer.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final String message;

	private ErrorResponse(String message) {
		this.message = message;
	}

	public static ErrorResponse from(Exception e) {

		return new ErrorResponse(e.getMessage());
	}
}
