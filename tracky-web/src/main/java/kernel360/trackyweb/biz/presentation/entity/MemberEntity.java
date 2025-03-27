package kernel360.trackyweb.biz.presentation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "biz_id")
	private String bizId;

	@Column(name = "member_id")
	private String memberId;

	private String pwd;

	private String email;

	private String role;

	@Column(name = "lastlogin_at")
	private LocalDateTime lastloginAt;

	@Column(name = "deleted_at")
	private LocalDateTime deleteAt;

	// 생성 메서드만 공개
	public static MemberEntity create(String bizId, String memberId, String pwd, String email, String role, LocalDateTime lastloginAt, LocalDateTime deleteAt) {
		MemberEntity member = new MemberEntity();
		member.bizId = bizId;
		member.memberId = memberId;
		member.pwd = pwd;
		member.email = email;
		member.role = role;
		member.lastloginAt = lastloginAt;
		member.deleteAt = deleteAt;
		return member;
	}
}
