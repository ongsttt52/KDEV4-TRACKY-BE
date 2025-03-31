package kernel360.trackyweb.rent.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.rent.application.dto.RentDetailResponse;
import kernel360.trackyweb.rent.application.dto.RentRequest;
import kernel360.trackyweb.rent.application.dto.RentResponse;
import kernel360.trackyweb.rent.infrastructure.repository.RentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService {

	private final RentRepository rentRepository;

	// UUID 자동 생성
	private String generateShortUUID() {
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
	 * @param rentRequest
	 * @return 등록 성공한 대여 detail
	 */
	public ApiResponse<RentDetailResponse> create(RentRequest rentRequest){

	}
}