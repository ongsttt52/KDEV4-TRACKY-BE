package kernel360.trackyweb.reservation.presentation.dto;

import kernel360.trackycore.core.common.entity.CarEntity;

public record CarResponse(
	String carPlate,   //번호판
	String carType,    //차종
	String carYear,    //연식
	String purpose,    //용도
	String status   //상태
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