package kernel360.trackyweb.reservation.presentation.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.RentEntity;

public record ReservationResponse(
	String rentUuid,    //예약 아이디
	String renterName,    //예약자 명
	String renterPhone,    //연락처
	String purpose,    //목적
	LocalDateTime rentStime,    //대여시간
	LocalDateTime rentEtime,    //반납시간
	String rentLoc,    //대여위치
	String returnLoc    //반납위치
) {
	public static ReservationResponse fromEntity(RentEntity rentEntity) {
		return new ReservationResponse(
			rentEntity.getRentUuid(),
			rentEntity.getRenterName(),
			rentEntity.getRenterPhone(),
			rentEntity.getPurpose(),
			rentEntity.getRentStime(),
			rentEntity.getRentEtime(),
			rentEntity.getRentLoc(),
			rentEntity.getReturnLoc()
		);
	}

}
