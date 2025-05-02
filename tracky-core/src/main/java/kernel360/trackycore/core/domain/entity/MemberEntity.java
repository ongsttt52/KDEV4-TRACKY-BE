package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import kernel360.trackycore.core.domain.entity.enums.MemberStatus;
import kernel360.trackycore.core.domain.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biz_id", nullable = false)
	private BizEntity biz;

	@Column(name = "member_id", length = 20, nullable = false, unique = true)
	private String memberId;

	@Column(name = "pwd", length = 100, nullable = false)
	private String pwd;

	@Column(name = "email", length = 254, nullable = false)
	private String email;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MemberStatus status;

	@Column(name = "lastlogin_at")
	private LocalDateTime lastLoginAt;

	@Column(name = "deleted_at")
	private LocalDateTime deleteAt;

	private MemberEntity(BizEntity biz,
		String memberId,
		String pwd,
		String email,
		Role role,
		MemberStatus status
	) {
		this.biz = biz;
		this.memberId = memberId;
		this.pwd = pwd;
		this.email = email;
		this.role = role;
		this.status = status;
	}

	public static MemberEntity create(BizEntity biz,
		String memberId,
		String pwd,
		String email,
		Role role,
		MemberStatus status
	) {
		return new MemberEntity(
			biz, memberId, pwd, email, role, status
		);
	}

	public void update(
		String bizName,
		String bizRegNum,
		String bizAdmin,
		String bizPhoneNum,
		String email,
		Role role,
		MemberStatus status
	) {
		this.biz.updateBizInfo(bizName, bizRegNum, bizAdmin, bizPhoneNum);
		this.email = email;
		this.role = role;
		this.status = status;
	}

	public void updateStatus(
		MemberStatus status
	) {
		this.status = status;
	}

	public void delete(
	) {
		this.status = MemberStatus.DELETED;
		this.deleteAt = LocalDateTime.now();
	}
}
