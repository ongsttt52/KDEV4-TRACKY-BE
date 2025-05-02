package kernel360.trackycore.core.domain.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
	ADMIN("ADMIN"),
	USER("USER");

	private final String label;

	Role(String label) {
		this.label = label;
	}
}
