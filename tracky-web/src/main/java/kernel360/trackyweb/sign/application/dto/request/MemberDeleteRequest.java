package kernel360.trackyweb.sign.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberDeleteRequest(
	@NotBlank(message = "ID가 없습니다.")
	String memberId
) { }
