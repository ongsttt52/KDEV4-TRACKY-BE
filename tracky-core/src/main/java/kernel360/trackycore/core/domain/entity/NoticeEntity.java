package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NoticeEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	private String title;

	private String content;

	private boolean isImportant;

	private LocalDateTime deletedAt;

	public static NoticeEntity create(String title, String content, boolean isImportant) {
		NoticeEntity notice = new NoticeEntity();
		notice.title = title;
		notice.content = content;
		notice.isImportant = isImportant;
		notice.onCreate();
		return notice;
	}

	public void update(String title, String content, boolean isImportant) {
		this.title = title;
		this.content = content;
		this.isImportant = isImportant;
		this.onUpdate();
	}

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}
}
