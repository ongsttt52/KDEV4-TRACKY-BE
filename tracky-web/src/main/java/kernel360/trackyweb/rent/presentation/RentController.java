package kernel360.trackyweb.rent.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kernel360.trackycore.core.common.ApiResponse;
import kernel360.trackyweb.rent.application.dto.RentDetailResponse;
import kernel360.trackyweb.rent.application.dto.RentRequest;
import kernel360.trackyweb.rent.application.dto.RentResponse;
import kernel360.trackyweb.rent.application.service.RentService;

@RestController
@RequestMapping("/api/rent")
public class RentController {

	private final RentService rentService;

	public RentController(RentService rentService) {
		this.rentService = rentService;
	}

	@GetMapping("/all")
	@Operation(summary = "대여 목록 전체 조회", description = "DB에 있는 모든 대여 리스트 조회")
	public ApiResponse<List<RentResponse>> getAll() {
		return rentService.getAll();
	}


	@PostMapping("/register")
	@Operation(summary = "대여 신규 등록", description = "대여 신규 등록 API")
	public ApiResponse<RentDetailResponse> create(
		@RequestBody RentRequest rentRequest
	){
		return rentService.create(rentRequest);
	}

}

