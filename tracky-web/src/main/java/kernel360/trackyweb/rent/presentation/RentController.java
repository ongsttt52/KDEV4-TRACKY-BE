package kernel360.trackyweb.rent.presentation;

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
import kernel360.trackyweb.rent.application.RentService;
import kernel360.trackyweb.rent.application.dto.request.RentCreateRequest;
import kernel360.trackyweb.rent.application.dto.request.RentOverLapRequest;
import kernel360.trackyweb.rent.application.dto.request.RentSearchByFilterRequest;
import kernel360.trackyweb.rent.application.dto.request.RentUpdateRequest;
import kernel360.trackyweb.rent.application.dto.response.OverlappingRentResponse;
import kernel360.trackyweb.rent.application.dto.response.RentMdnResponse;
import kernel360.trackyweb.rent.application.dto.response.RentResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController implements RentApiDocs {

	private final RentService rentService;

	@GetMapping("/mdns")
	public ApiResponse<List<RentMdnResponse>> getAllMdnByBizId(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	) {
		String bizUuid = memberPrincipal.bizUuid();

		return rentService.getAllMdnByBizUuid(bizUuid);
	}

	@GetMapping()
	public ApiResponse<List<RentResponse>> searchRentByFilter(
		@ModelAttribute RentSearchByFilterRequest rentSearchByFilterRequest,
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	) {
		String bizUuid = memberPrincipal.bizUuid();

		return rentService.searchRentByFilter(rentSearchByFilterRequest, bizUuid);
	}

	@GetMapping("/{rentUuid}")
	public ApiResponse<RentResponse> searchOne(
		@PathVariable String rentUuid
	) {
		return rentService.searchOne(rentUuid);
	}

	@PostMapping()
	public ApiResponse<RentResponse> create(
		@RequestBody RentCreateRequest rentCreateRequest
	) {
		return rentService.create(rentCreateRequest);
	}

	@PatchMapping("/{rentUuid}")
	public ApiResponse<RentResponse> update(
		@PathVariable String rentUuid,
		@RequestBody RentUpdateRequest rentUpdateRequest
	) {
		return rentService.update(rentUuid, rentUpdateRequest);
	}

	@DeleteMapping("/{rentUuid}")
	public ApiResponse<String> delete(
		@PathVariable String rentUuid
	) {
		return rentService.delete(rentUuid);
	}

	@PostMapping("/availability")
	public ApiResponse<List<OverlappingRentResponse>> validateOverlappingRentOps(
		@RequestBody RentOverLapRequest rentOverLapRequest
	) {
		return rentService.validateOverlappingRentOps(rentOverLapRequest);
	}

}
