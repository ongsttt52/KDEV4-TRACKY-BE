package kernel360.trackyweb.notice.application;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackycore.core.domain.entity.NoticeEntity;
import kernel360.trackyweb.notice.application.dto.request.NoticeCreateRequest;
import kernel360.trackyweb.notice.application.dto.request.NoticeSearchByFilterRequest;
import kernel360.trackyweb.notice.application.dto.request.NoticeUpdateRequest;
import kernel360.trackyweb.notice.application.dto.response.NoticeDetailResponse;
import kernel360.trackyweb.notice.application.dto.response.NoticeResponse;
import kernel360.trackyweb.notice.domain.provider.NoticeProvider;
import kernel360.trackyweb.sign.domain.provider.MemberProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeProvider noticeProvider;
	private final MemberProvider memberProvider;

	@Transactional
	public ApiResponse<NoticeDetailResponse> register(String memberId,
		NoticeCreateRequest noticeCreateRequest) {

		MemberEntity member = memberProvider.getMember(memberId);

		NoticeEntity notice = NoticeEntity.create(
			member,
			noticeCreateRequest.title(),
			noticeCreateRequest.content(),
			noticeCreateRequest.isImportant()
		);

		NoticeEntity savedNotice = noticeProvider.save(notice);

		NoticeDetailResponse response = NoticeDetailResponse.from(savedNotice);
		return ApiResponse.success(response);
	}

	@Transactional
	public ApiResponse<NoticeDetailResponse> update(Long id, NoticeUpdateRequest noticeUpdateRequest) {

		NoticeEntity notice = noticeProvider.get(id);
		notice.update(noticeUpdateRequest.title(), noticeUpdateRequest.content(),
			noticeUpdateRequest.isImportant());

		NoticeDetailResponse response = NoticeDetailResponse.from(notice);

		return ApiResponse.success(response);
	}

	@Transactional
	public ApiResponse<NoticeDetailResponse> delete(Long id) {

		NoticeEntity notice = noticeProvider.get(id);

		notice.softDelete();

		NoticeDetailResponse response = NoticeDetailResponse.from(notice);
		return ApiResponse.success(response);
	}

	@Transactional(readOnly = true)
	public ApiResponse<List<NoticeResponse>> getAllBySearchFilter(
		NoticeSearchByFilterRequest noticeSearchByFilterRequest) {

		Page<NoticeEntity> notices = noticeProvider.searchNoticeByFilter(
			noticeSearchByFilterRequest.search(),
			noticeSearchByFilterRequest.isImportant(),
			noticeSearchByFilterRequest.toPageable());

		Page<NoticeResponse> noticeResponses = notices.map(NoticeResponse::from);
		PageResponse pageResponse = PageResponse.from(noticeResponses);

		return ApiResponse.success(noticeResponses.getContent(), pageResponse);
	}
}
