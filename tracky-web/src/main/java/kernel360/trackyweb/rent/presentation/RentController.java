package kernel360.trackyweb.rent.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kernel360.trackycore.core.common.api.ApiResponse;
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


	@PostMapping("/register")
	public ApiResponse<RentResponse> create(
		@RequestBody RentRequest rentRequest
	){
		return rentService.create(rentRequest);
	}

}

