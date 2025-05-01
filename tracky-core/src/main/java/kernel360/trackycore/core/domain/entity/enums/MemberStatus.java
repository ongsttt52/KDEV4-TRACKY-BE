package kernel360.trackycore.core.domain.entity.enums;

import lombok.Getter;

@Getter
public enum MemberStatus {
	ACTIVE("활성화"),
	DEACTIVE("비활성화"),
	WAIT("대기중"),
	DELETED("삭제됨");

	private final String label;

	MemberStatus(String label) {
		this.label = label;
	}
}
