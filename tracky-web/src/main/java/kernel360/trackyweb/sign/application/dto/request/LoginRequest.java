package kernel360.trackyweb.sign.application.dto.request;

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
