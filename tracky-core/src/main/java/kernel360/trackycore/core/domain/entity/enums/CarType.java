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
}
