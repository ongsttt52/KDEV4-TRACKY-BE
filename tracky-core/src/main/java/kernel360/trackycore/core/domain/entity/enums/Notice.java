package kernel360.trackycore.core.domain.entity.enums;

import lombok.Getter;

@Getter
public enum Notice {
	IMPORTANT("중요"),
	NORMAL("일반");

	private final String label;

	Notice(String label) {
		this.label = label;
	}
}