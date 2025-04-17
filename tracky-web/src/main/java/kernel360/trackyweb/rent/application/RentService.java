package kernel360.trackyweb.rent.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.provider.CarProvider;
import kernel360.trackycore.core.common.sse.GlobalSseEvent;
import kernel360.trackycore.core.common.sse.SseEvent;
import kernel360.trackyweb.common.entity.RentEntity;
import kernel360.trackyweb.rent.application.dto.request.RentRequest;
import kernel360.trackyweb.rent.application.dto.response.RentResponse;
import kernel360.trackyweb.rent.domain.provider.RentDomainProvider;
import kernel360.trackyweb.rent.infrastructure.repository.RentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {

	private final RentDomainProvider rentDomainProvider;
	private final GlobalSseEvent globalSseEvent;

	private final CarProvider carProvider;

	private final RentRepositoryCustom rentRepositoryCustom;

	// 8자리 UUID 생성 메서드
	private String generateShortUuid() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	}

	/**
	 * 차량 mdn list 조회
	 * @return mdn list
	 */
	public ApiResponse<List<String>> getAllMdns() {
		List<String> mdns = rentDomainProvider.findAllMdns();

		return ApiResponse.success(mdns);
	}

	/**
	 * 필터링 기반 검색
	 * @param rentUuid
	 * @param rentStatus
	 * @param rentDate
	 * @return 검색된 예약 List
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<RentResponse>> searchByFilter(String rentUuid, String rentStatus, LocalDateTime rentDate,
		Pageable pageable) {
		Page<RentEntity> rents = rentRepositoryCustom.searchByFilter(rentUuid, rentStatus, rentDate, pageable);
		Page<RentResponse> rentResponses = rents.map(RentResponse::from);
		PageResponse pageResponse = PageResponse.from(rentResponses);
		return ApiResponse.success(rentResponses.getContent(), pageResponse);
	}

	/**
	 * rentUuid 값으로 검색
	 * @param rentUuid
	 * @return 수정된 대여 detail
	 */
	@Transactional(readOnly = true)
	public ApiResponse<RentResponse> searchOne(String rentUuid) {
		RentEntity rent = rentDomainProvider.getRent(rentUuid);
		return ApiResponse.success(RentResponse.from(rent));
	}

	/**
	 * 대여 신규 등록
	 * @param
	 * @return 등록 성공한 대여
	 */
	@Transactional
	public ApiResponse<RentResponse> create(RentRequest rentRequest) {
		CarEntity car = carProvider.findByMdn(rentRequest.mdn());

		String rentUuid = generateShortUuid();

		RentEntity rent = RentEntity.create(
			car,
			rentUuid,
			rentRequest.rentStime(),
			rentRequest.rentEtime(),
			rentRequest.renterName(),
			rentRequest.renterPhone(),
			rentRequest.purpose(),
			"reserved",
			rentRequest.rentLoc(),
			0,
			0,
			rentRequest.returnLoc(),
			0,
			0
		);

		RentEntity savedRent = rentDomainProvider.save(rent);

		RentResponse response = RentResponse.from(savedRent);

		globalSseEvent.sendEvent(SseEvent.RENT_CREATED);

		return ApiResponse.success(response);
	}

	/**
	 * 대여 정보 수정
	 * @param rentUuid
	 * @return 수정된 대여 detail
	 */
	@Transactional
	public ApiResponse<RentResponse> update(String rentUuid, RentRequest rentRequest) {
		RentEntity rent = rentDomainProvider.getRent(rentUuid);

		CarEntity car = carProvider.findByMdn(rentRequest.mdn());

		rent.update(car, rentRequest.rentStime(), rentRequest.rentEtime(), rentRequest.renterName(),
			rentRequest.renterPhone(),
			rentRequest.purpose(), rentRequest.rentStatus(), rentRequest.rentLoc(), rentRequest.returnLoc());

		globalSseEvent.sendEvent(SseEvent.RENT_UPDATED);

		return ApiResponse.success(RentResponse.from(rent));
	}

	/**
	 * 대여 삭제 API
	 * @param rentUuid
	 * @return ApiResponse
	 */
	@Transactional
	public ApiResponse<String> delete(String rentUuid) {
		rentDomainProvider.delete(rentUuid);
		globalSseEvent.sendEvent(SseEvent.RENT_DELETED);

		return ApiResponse.success("삭제 완료");
	}
}
