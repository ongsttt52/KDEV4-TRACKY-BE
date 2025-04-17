package kernel360.trackyemulator.infrastructure.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private LocalDateTime errorTime;
	private String code;
	private String message;

	public ErrorResponse(String code, String message) {
		this.code = code;
		this.message = message;
		this.errorTime = LocalDateTime.now();
	}

	public static ErrorResponse from(String code, String message) {
		return new ErrorResponse(code, message);
	}
}
