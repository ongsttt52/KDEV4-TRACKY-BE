package kernel360.trackycore.core.domain.entity.enums;

import lombok.Getter;

@Getter
public enum CarType {
	MINI("경차"),
	SEDAN("세단"),
	VAN("벤"),
	SUV("SUV"),
	TRUCK("트럭"),
	BUS("버스"),
	SPORTS("스포츠카"),
	ETC("기타");

	public final String label;

	CarType(String label) {
		this.label = label;
	}

	public static CarType from(String value) {
		if (value == null || value.isBlank())
			return null;
		try {
			return CarType.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
