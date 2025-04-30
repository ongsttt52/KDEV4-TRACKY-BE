package kernel360.trackyweb.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.common.webhook.Notifier;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebGlobalExceptionHandler {
	private final Notifier notifier;

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> handleWebGlobalException(GlobalException e) {
		return ResponseEntity
			.badRequest()
			.body(ErrorResponse.withDetail(e));
		//.body(ErrorResponse.from(e));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleWebGlobalException(Exception e) {
		notifier.notify(e);

		ErrorResponse errorResponse = new ErrorResponse(
			String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
			e.getMessage()
		);

		return ResponseEntity
			.internalServerError()
			.body(errorResponse);
	}
}
