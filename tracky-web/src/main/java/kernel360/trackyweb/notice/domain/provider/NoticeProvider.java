package kernel360.trackyweb.notice.domain.provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackycore.core.domain.entity.NoticeEntity;
import kernel360.trackyweb.notice.infrastructure.repository.NoticeDomainRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NoticeProvider {

	private final NoticeDomainRepository noticeDomainRepository;

	public NoticeEntity save(NoticeEntity notice) {
		return noticeDomainRepository.save(notice);
	}

	public NoticeEntity get(Long id) {
		return noticeDomainRepository.findById(id)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.NOTICE_NOT_FOUND));
	}

	public Page<NoticeEntity> searchNoticeByFilter(String search, Boolean isImportant, Pageable pageable) {
		return noticeDomainRepository.searchNoticeByFilter(search, isImportant, pageable);
	}
}
