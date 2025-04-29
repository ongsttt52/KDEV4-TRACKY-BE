package kernel360.trackyemulator.infrastructure.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class EmulatorExceptionHandler {

	// 커스텀 예외 처리 (400용)
	@ExceptionHandler(EmulatorException.class)
	public ResponseEntity<ErrorResponse> handleEmulatorException(EmulatorException e) {
		return ResponseEntity
			.badRequest()
			.body(ErrorResponse.from(
				e.getErrorCode().getCode(),
				e.getMessage()
			));
	}

	// 모든 예상치 못한 예외 처리 (500용)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
		log.error("예기치 못한 서버 에러 발생", e);

		return ResponseEntity
			.internalServerError()
			.body(ErrorResponse.from(
				ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
				ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public void handleFavicon(HttpClientErrorException.NotFound ex, HttpServletResponse response) throws IOException {
		// favicon 요청에 대해선 조용히 404 응답만 내려주고 로그는 남기지 않음
		response.setStatus(HttpStatus.NOT_FOUND.value());
	}
}
