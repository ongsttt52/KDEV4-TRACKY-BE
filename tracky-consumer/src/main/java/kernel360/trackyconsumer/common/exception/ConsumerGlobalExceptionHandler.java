package kernel360.trackyconsumer.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kernel360.trackycore.core.common.webhook.Notifier;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@io.swagger.v3.oas.annotations.Hidden
@RequiredArgsConstructor
public class ConsumerGlobalExceptionHandler {
	private final Notifier notifier;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		notifier.notify(e);
		return ResponseEntity
			.internalServerError()
			.body(ErrorResponse.from(e));
	}
}
