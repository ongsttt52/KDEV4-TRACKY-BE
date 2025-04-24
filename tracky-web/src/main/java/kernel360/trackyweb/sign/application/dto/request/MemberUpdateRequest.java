package kernel360.trackyweb.sign.application.dto.request;

public record MemberUpdateRequest(
	String memberId,
	String bizName,
	String bizRegNum,
	String bizAdmin,
	String bizPhoneNum,
	String email,
	String role,
	String status
) { }
