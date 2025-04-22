package kernel360.trackyweb.sign.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
	@NotBlank(message = "업체명을 입력해주세요.")
	String bizName,

	@NotBlank(message = "사업자 등록 번호를 입력해주세요.")
	String bizRegNum,

	@NotBlank(message = "담당자명을 입력해주세요.")
	String bizAdmin,

	@NotBlank(message = "담당자 전화번호를 입력해주세요.")
	String bizPhoneNum,

	@NotBlank(message = "아이디를 입력해주세요.")
	String memberId,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	String pwd,

	@Email(message = "ex) aa@aaa.aa")
	@NotBlank(message = "이메일을 입력해주세요.")
	String email
) {
}
