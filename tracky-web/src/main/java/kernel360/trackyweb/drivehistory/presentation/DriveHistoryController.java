package kernel360.trackyweb.drivehistory.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.drivehistory.application.DriveHistoryService;
import kernel360.trackyweb.drivehistory.domain.DriveHistory;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
public class DriveHistoryController implements DriveHistoryApiDocs {

	private final DriveHistoryService driveHistoryService;

	@GetMapping("/history")
	public ApiResponse<List<RentDriveHistory>> findAllRentHistories(
		@RequestParam(required = false) String rentUuid
	) {
		List<RentDriveHistory> result = driveHistoryService.getAllRentHistories(rentUuid);
		return ApiResponse.success(result);
	}

	@GetMapping("/history/{id}")
	public ApiResponse<List<DriveHistory>> getDriveHistories(@PathVariable Long id) {
		List<DriveHistory> histories = driveHistoryService.getDriveHistoriesByDriveid(id);
		return ApiResponse.success(histories);
	}
}
