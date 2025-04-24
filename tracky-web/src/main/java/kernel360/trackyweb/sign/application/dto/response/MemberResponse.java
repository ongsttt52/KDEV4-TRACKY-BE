package kernel360.trackyweb.sign.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import kernel360.trackycore.core.domain.entity.MemberEntity;

public record MemberResponse (
	String bizName,
	String bizRegNum,
	String bizAdmin,
	String bizPhoneNum,
	String memberId,
	String email,
	String role,
	String status,
	LocalDateTime createAt
) {

	public static MemberResponse from(MemberEntity member) {
		return new MemberResponse(
			member.getBiz().getBizName(),
			member.getBiz().getBizRegNum(),
			member.getBiz().getBizAdmin(),
			member.getBiz().getBizPhoneNum(),
			member.getMemberId(),
			member.getEmail(),
			member.getRole(),
			member.getStatus(),
			member.getCreatedAt()
		);
	}

	public static List<MemberResponse> fromList (List<MemberEntity> memberList) {
		return memberList.stream()
			.map(MemberResponse::from).toList();
	}
}
