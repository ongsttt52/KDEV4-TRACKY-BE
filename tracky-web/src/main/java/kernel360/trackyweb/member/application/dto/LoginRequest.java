package kernel360.trackyweb.member.application.dto;

public class LoginRequest {
	private String memberId;
	private String pwd;

	public String getMemberId() {return memberId;}
	public void setMemberId(String memberId) {this.memberId = memberId;}

	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }
}
