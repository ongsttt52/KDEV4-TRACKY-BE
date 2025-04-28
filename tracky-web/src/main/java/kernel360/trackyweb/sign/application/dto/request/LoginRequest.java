package kernel360.trackyweb.sign.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
	@NotBlank(message = "ID를 입력해주세요")
	String memberId,

	@NotBlank(message = "비밀번호를 입력해주세요")
	String pwd
) {
	public LoginRequest(
		String memberId, String pwd
	) {
		this.memberId = memberId;
		this.pwd = pwd;
	}
}
