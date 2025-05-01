package kernel360.trackyweb.sign.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kernel360.trackycore.core.domain.entity.enums.MemberStatus;
import kernel360.trackycore.core.domain.entity.enums.Role;

public record MemberUpdateRequest(
	@NotBlank(message = "ID를 입력해주세요.")
	String memberId,

	@NotBlank(message = "업체명을 입력해주세요.")
	String bizName,

	@NotBlank(message = "사업자 등록 번호를 입력해주세요.")
	String bizRegNum,

	@NotBlank(message = "담당자 이름를 입력해주세요.")
	String bizAdmin,

	@NotBlank(message = "담당자 전화번호를 입력해주세요.")
	String bizPhoneNum,

	@Email(message = "ex) aa@aaa.aa")
	@NotBlank(message = "이메일을 입력해주세요.")
	String email,

	@NotBlank(message = "권한을 입력해주세요.")
	Role role,

	@NotBlank(message = "상태를 입력해주세요.")
	MemberStatus status
) {
}
