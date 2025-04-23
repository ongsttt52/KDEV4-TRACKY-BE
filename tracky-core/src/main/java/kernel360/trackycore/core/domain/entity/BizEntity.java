package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "biz")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BizEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "biz_uuid", length = 100, nullable = false, unique = true)
	private String bizUuid;

	@Column(name = "biz_name", length = 100, nullable = false)
	private String bizName;

	@Column(name = "biz_reg_num", length = 20, nullable = false)
	private String bizRegNum;

	@Column(name = "biz_admin", length = 100, nullable = false)
	private String bizAdmin;

	@Column(name = "biz_phone_num", length = 20, nullable = false)
	private String bizPhoneNum;

	@Column(name = "deleted_at")
	private LocalDateTime deleteAt;

	// 생성 메서드만 공개
	public static BizEntity create(String bizName, String bizUuid, String bizRegNum, String bizAdmin,
		String bizPhoneNum,
		LocalDateTime deleteAt) {
		BizEntity biz = new BizEntity();
		biz.bizName = bizName;
		biz.bizUuid = bizUuid;
		biz.bizRegNum = bizRegNum;
		biz.bizAdmin = bizAdmin;
		biz.bizPhoneNum = bizPhoneNum;
		biz.deleteAt = deleteAt;
		return biz;
	}
}
