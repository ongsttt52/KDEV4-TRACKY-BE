package kernel360.trackycore.core.infrastructure.exception;

public class ExampleNotFoundException extends GlobalException {
	public ExampleNotFoundException() {
		super(ErrorCode.EXAMPL_NOT_FOUND);
	}
}
