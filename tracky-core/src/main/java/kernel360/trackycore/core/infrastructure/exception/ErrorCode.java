package kernel360.trackycore.core.infrastructure.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public enum ErrorCode {
	EXAMPL_NOT_FOUND("EXAMPL_404", "에러 샘플 코드입니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
