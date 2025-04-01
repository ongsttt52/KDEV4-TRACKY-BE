package kernel360.trackyweb.rent.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.car.presentation.dto.CarResponse;
import kernel360.trackyweb.rent.application.dto.RentDetailResponse;
import kernel360.trackyweb.rent.application.dto.RentRequest;
import kernel360.trackyweb.rent.application.dto.RentResponse;
import kernel360.trackyweb.rent.application.service.RentService;

@RestController
@RequestMapping("/api/rents")
public class RentController implements RentApiDocs {

	private final RentService rentService;

	public RentController(RentService rentService) {
		this.rentService = rentService;
	}

	@GetMapping("/all")
	public ApiResponse<List<RentResponse>> getAll() {
		return rentService.getAll();
	}


	@GetMapping("/search")
	public ApiResponse<List<RentResponse>> searchByRentUuid(
		@RequestParam String rentUuid
	) {
		return rentService.searchByRentUuid(rentUuid);
	}

	@PostMapping("/create")
	public ApiResponse<RentResponse> create(
		@RequestBody RentRequest rentRequest
	){
		return rentService.create(rentRequest);
	}

}