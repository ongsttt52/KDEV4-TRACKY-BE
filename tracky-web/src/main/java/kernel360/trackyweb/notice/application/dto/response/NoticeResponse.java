package kernel360.trackyweb.notice.application.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel360.trackycore.core.domain.entity.NoticeEntity;

public record NoticeResponse(
	long id,
	String title,
	String content,
	boolean isImportant,
	@JsonFormat(pattern = "yyyy년 M월 d일")
	LocalDateTime createdAt
) {
	public static NoticeResponse from(NoticeEntity notice) {
		return new NoticeResponse(
			notice.getId(),
			notice.getTitle(),
			notice.getContent(),
			notice.isImportant(),
			notice.getCreatedAt()
		);
	}
}
