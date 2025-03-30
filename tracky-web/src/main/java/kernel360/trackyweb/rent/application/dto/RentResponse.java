package kernel360.trackyweb.rent.application.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.infrastructure.entity.RentEntity;
import lombok.Getter;

@Getter
public class RentResponse {
	private final Long id;
	private final String renterName;
	private final String renterPhone;
	private final LocalDateTime rentStime;
	private final LocalDateTime rentEtime;
	private final String rentLoc;
	private final String returnLoc;
	private final String purpose;

	// 생성자
	public RentResponse(Long id, String renterName, String renterPhone,
		LocalDateTime rentStime, LocalDateTime rentEtime,
		String rentLoc, String returnLoc, String purpose) {
		this.id = id;
		this.renterName = renterName;
		this.renterPhone = renterPhone;
		this.rentStime = rentStime;
		this.rentEtime = rentEtime;
		this.rentLoc = rentLoc;
		this.returnLoc = returnLoc;
		this.purpose = purpose;
	}

	// 정적 팩토리 메서드
	public static RentResponse from(RentEntity entity) {
		return new RentResponse(
			entity.getId(),
			entity.getRenterName(),
			entity.getRenterPhone(),
			entity.getRentStime(),
			entity.getRentEtime(),
			entity.getRentLoc(),
			entity.getReturnLoc(),
			entity.getPurpose()
		);
	}
}