package kernel360.trackyconsumer.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@io.swagger.v3.oas.annotations.Hidden
public class ConsumerGlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		return ResponseEntity
			.internalServerError()
			.body(ErrorResponse.from(e));
	}
}
