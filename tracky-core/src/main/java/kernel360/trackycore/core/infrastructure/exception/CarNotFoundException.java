package kernel360.trackycore.core.infrastructure.exception;

public class CarNotFoundException extends GlobalException {
	public CarNotFoundException() {
		super(ErrorCode.CAR_NOT_FOUND);
	}
}
