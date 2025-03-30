package kernel360.trackyweb.rent.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.infrastructure.entity.RentEntity;
import kernel360.trackyweb.rent.application.dto.RentRequest;
import kernel360.trackyweb.rent.application.dto.RentResponse;
import kernel360.trackyweb.rent.infrastructure.repository.RentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService {

	private final RentRepository rentRepository;

	// 차량 등록
	@Transactional
	public RentResponse crate(RentRequest request) {
		RentEntity entity = new RentEntity(
			UUID.randomUUID().toString(),
			request.getRentStime(),
			request.getRentEtime(),
			request.getRenterName(),
			request.getRenterPhone(),
			request.getPurpose(),
			"RENTED",
			request.getRentLoc(),
			request.getRentLat(),
			request.getRentLon(),
			request.getReturnLoc(),
			request.getReturnLat(),
			request.getReturnLon()
		);
		return RentResponse.from(rentRepository.save(entity));
	}

	@Transactional(readOnly = true)
	public List<RentResponse> findAll() {
		return rentRepository.findAll().stream()
			.map(RentResponse::from)
			.toList();
	}

}
