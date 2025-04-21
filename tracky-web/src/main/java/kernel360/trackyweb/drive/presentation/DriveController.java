package kernel360.trackyweb.drive.presentation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.drive.application.DriveService;
import kernel360.trackyweb.drive.application.dto.request.DriveSearchFilterRequest;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import kernel360.trackyweb.drive.application.dto.response.DriveListResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
public class DriveController {

	private final DriveService driveService;

	@GetMapping("/cars")
	public ApiResponse<List<CarListResponse>> getCar() {
		return driveService.getCar();
	}

	@GetMapping()
	public ApiResponse<List<DriveListResponse>> getBySearchFilter(
		@RequestParam(required = false) String search,
		Pageable pageable
	) {
		DriveSearchFilterRequest driveSearchFilterRequest = new DriveSearchFilterRequest(search, pageable);
		return driveService.getBySearchFilter(driveSearchFilterRequest);
	}
}
