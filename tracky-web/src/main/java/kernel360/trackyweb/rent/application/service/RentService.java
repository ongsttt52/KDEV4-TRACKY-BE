package kernel360.trackyweb.rent.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackyweb.rent.application.dto.RentDetailResponse;
import kernel360.trackyweb.rent.application.dto.RentRequest;
import kernel360.trackyweb.rent.application.dto.RentResponse;
import kernel360.trackyweb.rent.infrastructure.repository.CarRepository;
import kernel360.trackyweb.rent.infrastructure.repository.RentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService {

	private final RentRepository rentRepository;
	@Qualifier("carRepositoryForRent") // 4월 1일 공통화 작업
	private final CarRepository carRepository;

	// 8자리 UUID 생성 메서드
	private String generateShortUuid() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	}

	/**
	 * 렌트 정보 전체 조회
	 */
	public ApiResponse<List<RentResponse>> getAll() {
		return ApiResponse.success(rentRepository.findAll().stream()
			.map(RentResponse::from)
			.collect(Collectors.toList()));
	}

	/**
	 * 대여 신규 등록
	 * @param
	 * @return 등록 성공한 대여
	 */
	public ApiResponse<RentResponse> create(RentRequest rentRequest) {
		CarEntity car = carRepository.findByMdn(rentRequest.mdn())
			.orElseThrow(() -> CarException.notFound());

		// RentEntity 생성 (차량과 대여 정보 포함, uuid 만들기 (임시 8자리)
		String rentUuid = generateShortUuid();

		// 구지원 - 임시로 예약 등록은 전부 '대여 전'
		RentEntity rent = rentRequest.toEntity(rentUuid, "대여 전");

		RentEntity savedRent = rentRepository.save(rent);

		RentResponse response = RentResponse.from(savedRent);

		return ApiResponse.success(response);
	}
}