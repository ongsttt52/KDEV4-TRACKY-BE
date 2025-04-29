package kernel360.trackyweb.car.presentation;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.car.application.CarService;
import kernel360.trackyweb.car.application.dto.request.CarCreateRequest;
import kernel360.trackyweb.car.application.dto.request.CarDeleteRequest;
import kernel360.trackyweb.car.application.dto.request.CarSearchByFilterRequest;
import kernel360.trackyweb.car.application.dto.request.CarUpdateRequest;
import kernel360.trackyweb.car.application.dto.response.CarDetailResponse;
import kernel360.trackyweb.car.application.dto.response.CarResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController implements CarApiDocs {

	private final CarService carService;

	@GetMapping("/check/mdn/{mdn}")
	public ApiResponse<Boolean> existsByMdn(
		@PathVariable String mdn
	) {
		return carService.existsByMdn(mdn);
	}

	@GetMapping()
	public ApiResponse<List<CarResponse>> getAllBySearchFilter(
		@ModelAttribute CarSearchByFilterRequest carSearchByFilterRequest,
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	) {
		return carService.getAllBySearchFilter(memberPrincipal.bizUuid(), carSearchByFilterRequest);
	}

	@GetMapping("/{mdn}")
	public ApiResponse<CarDetailResponse> searchOne(
		@PathVariable String mdn
	) {
		return carService.searchOne(mdn);
	}

	@PostMapping("")
	public ApiResponse<CarDetailResponse> create(
		@RequestBody CarCreateRequest carCreateRequest
	) {
		return carService.create(carCreateRequest);
	}

	@PatchMapping("")
	public ApiResponse<CarDetailResponse> update(
		@RequestBody CarUpdateRequest carUpdateRequest
	) {
		return carService.update(carUpdateRequest);
	}

	@DeleteMapping("")
	public ApiResponse<CarDetailResponse> delete(
		@RequestBody CarDeleteRequest carDeleteRequest
	) {
		return carService.delete(carDeleteRequest);
	}
}
