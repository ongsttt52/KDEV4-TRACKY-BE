package kernel360.trackyweb.notice.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel360.trackycore.core.domain.entity.NoticeEntity;

public interface NoticeDomainRepositoryCustom {

	Page<NoticeEntity> searchNoticeByFilter(String search, Boolean isImportant, Pageable pageable);

	List<NoticeEntity> findAllByIsDeletedFalse();

}
