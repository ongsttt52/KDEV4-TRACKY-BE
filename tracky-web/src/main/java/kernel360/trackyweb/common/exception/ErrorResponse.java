package kernel360.trackyweb.common.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kernel360.trackycore.core.common.exception.GlobalException;
import lombok.Getter;

@Getter
@Schema(description = "공통 에러 응답")
public class ErrorResponse {

	@Schema(description = "에러 발생 시간")
	private LocalDateTime errorTime;

	@Schema(description = "에러 코드")
	private String code;

	@Schema(description = "에러 메세지")
	private String message;

	@Schema(description = "에러 데이터")
	private Object data;

	public ErrorResponse(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.errorTime = LocalDateTime.now();
		this.data = data;
	}

	public static ErrorResponse from(GlobalException e) {
		return new ErrorResponse(
			e.getErrorCode().getCode(),
			e.getErrorCode().getMessage(),
			e.getData()
		);
	}
}
