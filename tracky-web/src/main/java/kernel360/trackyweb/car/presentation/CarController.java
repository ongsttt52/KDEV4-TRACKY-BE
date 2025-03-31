package kernel360.trackyweb.car.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.car.application.CarService;
import kernel360.trackyweb.car.presentation.dto.CarDetailResponse;
import kernel360.trackyweb.car.presentation.dto.CarRequest;
import kernel360.trackyweb.car.presentation.dto.CarResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController implements CarApiDocs {

	private final CarService carService;

	@GetMapping("all")
	@Operation(summary = "전체 차량 조회", description = "DB에 있는 모든 차량 리스트 조회")
	public ApiResponse<List<CarResponse>> getAll() {
		return carService.getAll();
	}

	@GetMapping("/search")
	public ApiResponse<List<CarResponse>> searchByMdn(
		@RequestParam String mdn
 	) {
		return carService.searchByMdn(mdn);
	}

	@GetMapping("/search/{id}")
	public ApiResponse<CarResponse> searchById(
		@PathVariable Long id
	) {
		return carService.searchById(id);
	}

	@GetMapping("/search/{id}/detail")
	public ApiResponse<CarDetailResponse> searchDetailById(
		@PathVariable Long id
	) {
		return carService.searchDetailById(id);
	}

	@PostMapping("/register")
	@Operation(summary = "차량 신규 등록", description = "차량 신규 등록 API")
	public ApiResponse<CarDetailResponse> create(
		@RequestBody CarRequest carRequest
	) {
		return carService.create(carRequest);
	}

}
