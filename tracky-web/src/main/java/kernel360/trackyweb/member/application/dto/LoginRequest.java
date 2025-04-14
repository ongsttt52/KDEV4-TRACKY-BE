package kernel360.trackyweb.member.application.dto;

public record LoginRequest(
	String memberId,
	String pwd
) {
	public LoginRequest(
		String memberId, String pwd
	) {
		this.memberId = memberId;
		this.pwd = pwd;
	}
}
