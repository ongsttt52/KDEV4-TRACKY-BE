package kernel360.trackyweb.car.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.ApiResponse;
import kernel360.trackyweb.car.application.CarService;
import kernel360.trackyweb.car.application.dto.CarDetailResponse;
import kernel360.trackyweb.car.application.dto.CarResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController implements CarApiDocs {

	private final CarService carService;

	@GetMapping("/all")
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

}
