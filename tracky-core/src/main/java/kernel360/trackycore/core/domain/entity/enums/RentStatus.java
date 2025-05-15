package kernel360.trackycore.core.domain.entity.enums;

import lombok.Getter;

@Getter
public enum RentStatus {
	RESERVED("예약완료"),
	RENTING("대여중"),
	RETURNED("반납완료"),
	CANCELED("취소"),
	DELETED("삭제됨");

	private final String label;

	RentStatus(String label) {
		this.label = label;
	}

	public static RentStatus from(String value) {
		if (value == null || value.isBlank())
			return null;
		try {
			return RentStatus.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
