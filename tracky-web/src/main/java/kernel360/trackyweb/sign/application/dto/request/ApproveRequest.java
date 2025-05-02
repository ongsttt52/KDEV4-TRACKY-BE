package kernel360.trackyweb.sign.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import kernel360.trackycore.core.domain.entity.enums.MemberStatus;

public record ApproveRequest(
	@NotBlank(message = "ID를  입력해주세요")
	String memberId,

	@NotBlank(message = "상태를 입력하세요")
	MemberStatus status
) {
}
