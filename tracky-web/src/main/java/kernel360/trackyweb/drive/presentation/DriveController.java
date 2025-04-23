package kernel360.trackyweb.drive.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.drive.application.DriveService;
import kernel360.trackyweb.drive.application.dto.request.CarListRequest;
import kernel360.trackyweb.drive.application.dto.request.DriveListRequest;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import kernel360.trackyweb.drive.application.dto.response.DriveListResponse;
import kernel360.trackyweb.drive.domain.DriveHistory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
public class DriveController {

	private final DriveService driveService;

	//운향기록 페이지 왼편 차 리스트
	@GetMapping("/cars")
	public ApiResponse<List<CarListResponse>> getCarListBySearchFilter(
		@ModelAttribute CarListRequest carListSearchFilterRequest
	) {
		return driveService.getCarListBySearchFilter(carListSearchFilterRequest);
	}

	@GetMapping()
	public ApiResponse<List<DriveListResponse>> getBySearchFilter(
		@ModelAttribute DriveListRequest driveListRequest
	) {
		return driveService.getDrivesBySearchFilter(driveListRequest);
	}

	//운행기록 상세
	@GetMapping("/{driveId}")
	public ApiResponse<DriveHistory> getDriveHistroy(
		@PathVariable Long driveId
	) {
		DriveHistory history = driveService.getDriveHistroy(driveId);
		return ApiResponse.success(history);
	}
}
