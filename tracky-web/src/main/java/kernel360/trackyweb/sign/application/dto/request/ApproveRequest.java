package kernel360.trackyweb.sign.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ApproveRequest (
	@NotBlank
	String memberId
) {}
