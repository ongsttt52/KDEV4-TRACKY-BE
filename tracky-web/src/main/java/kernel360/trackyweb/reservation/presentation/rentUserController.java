package kernel360.trackyweb.reservation.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.reservation.application.ReservationService;
import kernel360.trackyweb.reservation.presentation.dto.ReservationDrivingsResponse;
import kernel360.trackyweb.reservation.presentation.dto.ReservationWithCarResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class rentUserController {

	private final ReservationService reservationService;

	@GetMapping("/{uuid}")
	public ApiResponse<ReservationWithCarResponse> searchRentWithCarByUuid(@PathVariable String uuid) {
		return reservationService.searchRentWithCarByUuid(uuid);
	}

	@GetMapping("/{uuid}/drivings")
	public ApiResponse<List<ReservationDrivingsResponse>> searchDrivingsByUuid(@PathVariable String uuid) {
		return reservationService.searchDrivingsByUuid(uuid);
	}

}
