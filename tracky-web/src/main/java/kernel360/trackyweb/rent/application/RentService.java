package kernel360.trackyweb.rent.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.provider.CarProvider;
import kernel360.trackycore.core.domain.provider.RentProvider;
import kernel360.trackyweb.common.sse.GlobalSseEvent;
import kernel360.trackyweb.common.sse.SseEvent;
import kernel360.trackyweb.rent.application.dto.request.RentCreateRequest;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.request.RentUpdateRequest;
import kernel360.trackyweb.rent.application.dto.response.RentResponse;
import kernel360.trackyweb.rent.domain.provider.RentDomainProvider;
import kernel360.trackyweb.rent.domain.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {

	private final RentDomainProvider rentDomainProvider;
	private final RentProvider rentProvider;
	private final CarProvider carProvider;
	private final GlobalSseEvent globalSseEvent;

	/**
	 * 차량 mdn list 조회
	 *
	 * @return mdn list
	 */
	public ApiResponse<List<String>> getAllMdnByBizId(String bizUuid) {
		List<String> mdns = rentDomainProvider.getAllMdnByBizId(bizUuid);
		return ApiResponse.success(mdns);
	}

	/**
	 * 필터링 기반 검색
	 *
	 * @param rentUuid
	 * @param rentStatus
	 * @param rentDate
	 * @return 검색된 예약 List
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<RentResponse>> searchRentByFilter(RentSearchByFilterRequest rentSearchByFilterRequest,
		String bizUuid) {
		Page<RentEntity> rents = rentDomainProvider.searchRentByFilter(rentSearchByFilterRequest, bizUuid);
		Page<RentResponse> rentResponses = rents.map(RentResponse::from);
		PageResponse pageResponse = PageResponse.from(rentResponses);
		return ApiResponse.success(rentResponses.getContent(), pageResponse);
	}

	/**
	 * rentUuid 값으로 검색
	 *
	 * @param rentUuid
	 * @return 수정된 대여 detail
	 */
	@Transactional(readOnly = true)
	public ApiResponse<RentResponse> searchOne(String rentUuid) {
		RentEntity rent = rentProvider.getRent(rentUuid);
		return ApiResponse.success(RentResponse.from(rent));
	}

	/**
	 * 대여 신규 등록
	 *
	 * @param
	 * @return 등록 성공한 대여
	 */
	@Transactional
	public ApiResponse<RentResponse> create(RentCreateRequest rentCreateRequest) {
		CarEntity car = carProvider.findByMdn(rentCreateRequest.mdn());

		rentDomainProvider.validateOverlappingRent(
			car.getMdn(),
			rentCreateRequest.rentStime(),
			rentCreateRequest.rentEtime()
		);

		String rentUuid = UuidGenerator.generateUuid();

		RentEntity rent = RentEntity.create(
			car,
			rentUuid,
			rentCreateRequest.rentStime(),
			rentCreateRequest.rentEtime(),
			rentCreateRequest.renterName(),
			rentCreateRequest.renterPhone(),
			rentCreateRequest.purpose(),
			"reserved",
			rentCreateRequest.rentLoc(),
			rentCreateRequest.rentLat(),
			rentCreateRequest.rentLon(),
			rentCreateRequest.returnLoc(),
			rentCreateRequest.returnLat(),
			rentCreateRequest.returnLon()
		);

		RentEntity savedRent = rentDomainProvider.save(rent);

		RentResponse response = RentResponse.from(savedRent);

		globalSseEvent.sendEvent(SseEvent.RENT_CREATED);

		return ApiResponse.success(response);
	}

	/**
	 * 대여 정보 수정
	 *
	 * @param rentUuid
	 * @return 수정된 대여 detail
	 */
	@Transactional
	public ApiResponse<RentResponse> update(String rentUuid, RentUpdateRequest rentUpdateRequest) {
		RentEntity rent = rentProvider.getRent(rentUuid);

		CarEntity car = carProvider.findByMdn(rentUpdateRequest.mdn());

		rent.update(car, rentUpdateRequest.rentStime(), rentUpdateRequest.rentEtime(), rentUpdateRequest.renterName(),
			rentUpdateRequest.renterPhone(),
			rentUpdateRequest.purpose(), rentUpdateRequest.rentStatus(), rentUpdateRequest.rentLoc(),
			rentUpdateRequest.returnLoc());

		globalSseEvent.sendEvent(SseEvent.RENT_UPDATED);

		return ApiResponse.success(RentResponse.from(rent));
	}

	/**
	 * 대여 삭제 API
	 *
	 * @param rentUuid
	 * @return ApiResponse
	 */
	@Transactional
	public ApiResponse<String> delete(String rentUuid) {
		// rentDomainProvider.delete(rentUuid);
		rentDomainProvider.softDelete(rentUuid);
		globalSseEvent.sendEvent(SseEvent.RENT_DELETED);

		return ApiResponse.success("삭제 완료");
	}
}
