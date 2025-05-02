package kernel360.trackyweb.car.domain.provider;

import kernel360.trackyweb.dashboard.domain.CarStatus;

public record MdnStatus(
	String mdn,
	String carPlate,
	CarStatus status
) {

}
