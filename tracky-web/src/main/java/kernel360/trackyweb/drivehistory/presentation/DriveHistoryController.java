package kernel360.trackyweb.drivehistory.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.drivehistory.application.DriveHistoryService;
import kernel360.trackyweb.drivehistory.domain.CarDriveHistory;
import kernel360.trackyweb.drivehistory.domain.DriveHistoryDto;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistoryDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
public class DriveHistoryController implements DriveHistoryApiDocs {

	private final DriveHistoryService driveHistoryService;

	@GetMapping("/history")
	public ApiResponse<List<RentDriveHistoryDto>> findAllRentHistories() {
		List<RentDriveHistoryDto> result = driveHistoryService.getAllRentHistories();
		return ApiResponse.success(result);
	}

	@GetMapping("/history/{id}")
	public ApiResponse<List<DriveHistoryDto>> getDriveHistories(@PathVariable Long id) {
		List<DriveHistoryDto> histories = driveHistoryService.getDriveHistoriesByDriveid(id);
		return ApiResponse.success(histories);
	}

	@GetMapping("/history/cars")
	public ApiResponse<List<CarDriveHistory>> getDriveHistoriesByCar(
		@RequestParam(required = true) String mdn
	) {
		List<CarDriveHistory> histories = driveHistoryService.getDriveHistoriesByCar(mdn);
		return ApiResponse.success(histories);
	}
}
