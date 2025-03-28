package kernel360.trackyweb.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kernel360.trackycore.core.infrastructure.exception.GlobalException;

@RestControllerAdvice
@io.swagger.v3.oas.annotations.Hidden
public class WebGlobalExceptionHandler {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> handleWebGlobalException(GlobalException e) {
		return ResponseEntity
			.badRequest()
			.body(ErrorResponse.from(e));
	}
}
