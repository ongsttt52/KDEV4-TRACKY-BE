package kernel360.trackyweb.reservation.presentation.dto;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarResponse(
	String carPlate,   //번호판
	CarType carType,    //차종
	String carYear,    //연식
	String purpose,    //용도
	CarStatus status   //상태
) {
	public static CarResponse fromEntity(CarEntity entity) {
		return new CarResponse(
			entity.getCarPlate(),
			entity.getCarType(),
			entity.getCarYear(),
			entity.getPurpose(),
			entity.getStatus()
		);
	}
}
