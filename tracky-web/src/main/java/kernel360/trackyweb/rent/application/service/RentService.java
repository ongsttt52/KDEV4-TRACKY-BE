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
	@Qualifier("carRepositoryForRent")
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
	 * @return 등록 성공한 대여 detail
	 */
	public ApiResponse<RentResponse> create(RentRequest rentRequest) {
		// 1. mdn으로 차량 조회
		CarEntity car = carRepository.findByMdn(rentRequest.mdn())
			.orElseThrow(() -> CarException.notFound());

		// 2. RentEntity 생성 (차량과 대여 정보 포함)
		String rentUuid = generateShortUuid();
		RentEntity rent = rentRequest.toEntity(rentUuid, "PENDING");

		// 3. DB에 저장
		RentEntity savedRent = rentRepository.save(rent);

		// 4. 응답 DTO로 변환
		RentResponse response = RentResponse.from(savedRent);

		// 5. 성공 응답 반환
		return ApiResponse.success(response);
	}
}