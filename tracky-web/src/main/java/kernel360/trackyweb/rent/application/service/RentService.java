package kernel360.trackyweb.rent.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackycore.core.infrastructure.exception.RentException;
import kernel360.trackyweb.emitter.EventEmitterService;
import kernel360.trackyweb.rent.application.mapper.RentEvent;
import kernel360.trackyweb.rent.application.mapper.RentMapper;
import kernel360.trackyweb.rent.infrastructure.repository.CarRepository;
import kernel360.trackyweb.rent.infrastructure.repository.RentRepository;
import kernel360.trackyweb.rent.presentation.dto.RentRequest;
import kernel360.trackyweb.rent.presentation.dto.RentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {

	private final RentRepository rentRepository;
	@Qualifier("carRepositoryForRent") // 4월 1일 공통화 작업
	private final CarRepository carRepository;

	// sse emitter
	private final EventEmitterService eventEmitterService;

	// 8자리 UUID 생성 메서드
	private String generateShortUuid() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	}

	/**
	 * 렌트 정보 전체 조회
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<RentResponse>> getAll() {
		return ApiResponse.success(rentRepository.findAll().stream()
			.map(RentResponse::from)
			.collect(Collectors.toList()));
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
		Page<RentEntity> rents = rentRepository.searchByFilters(rentUuid, rentStatus, rentDate, pageable);
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
	public ApiResponse<RentResponse> searchDetailByRentUuid(String rentUuid) {
		RentEntity rent = rentRepository.findDetailByRentUuid(rentUuid)
			.orElseThrow(() -> RentException.notFound());
		return ApiResponse.success(RentResponse.from(rent));
	}

	/**
	 * 대여 신규 등록
	 * @param
	 * @return 등록 성공한 대여
	 */
	@Transactional
	public ApiResponse<RentResponse> create(RentRequest rentRequest) {
		CarEntity car = carRepository.findByMdn(rentRequest.mdn())
			.orElseThrow(() -> CarException.notFound());

		// RentEntity 생성 (차량과 대여 정보 포함, uuid 만들기 (임시 8자리)
		String rentUuid = generateShortUuid();

		// 구지원 - 임시로 예약 등록은 전부 '대여 전'
		RentEntity rent = rentRequest.toEntity(car, rentUuid, "대여 전");

		RentEntity savedRent = rentRepository.save(rent);

		RentResponse response = RentResponse.from(savedRent);

		RentEvent rentEvent = RentEvent.create("rent_event", "create", "예약을 생성 하였습니다.");
		eventEmitterService.sendEvent("rent_event", rentEvent);

		return ApiResponse.success(response);
	}

	/**
	 * 대여 정보 수정
	 * @param rentUuid
	 * @return 수정된 대여 detail
	 */
	@Transactional
	public ApiResponse<RentResponse> update(String rentUuid, RentRequest rentRequest) {
		RentEntity rent = rentRepository.findDetailByRentUuid(rentUuid)
			.orElseThrow(() -> RentException.notFound());

		CarEntity car = carRepository.findByMdn(rentRequest.mdn())
			.orElseThrow(() -> CarException.notFound());

		RentMapper.updateRent(car, rent, rentRequest);

		log.info("업테이트 대여 : {}", rent);

		RentEntity updatedRent = rentRepository.save(rent);

		RentEvent rentEvent = RentEvent.create("rent_event", "update", "예약을 수정 하였습니다.");
		eventEmitterService.sendEvent("rent_event", rentEvent);

		return ApiResponse.success(RentResponse.from(updatedRent));
	}

	/**
	 * 대여 삭제 API
	 * @param rentUuid
	 * @return ApiResponse
	 */
	@Transactional
	public ApiResponse<String> delete(String rentUuid) {
		rentRepository.deleteByRentUuid(rentUuid);

		RentEvent rentEvent = RentEvent.create("rent_event", "delete", "예약을 삭제 하였습니다.");
		eventEmitterService.sendEvent("rent_event", rentEvent);

		return ApiResponse.success("삭제 완료");
	}
}
