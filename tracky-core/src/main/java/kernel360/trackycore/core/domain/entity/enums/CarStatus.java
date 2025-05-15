package kernel360.trackycore.core.domain.entity.enums;

import lombok.Getter;

@Getter
public enum CarStatus {
	RUNNING("운행중"),
	WAITING("대기중"),
	FIXING("정비중"),
	CLOSED("폐차"),
	DELETED("삭제됨");

	private final String label;

	CarStatus(String label) {
		this.label = label;
	}

	public static CarStatus from(String value) {
		if (value == null || value.isBlank())
			return null;
		try {
			return CarStatus.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
