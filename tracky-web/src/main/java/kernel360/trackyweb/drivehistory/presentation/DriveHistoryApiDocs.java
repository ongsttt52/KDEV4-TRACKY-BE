package kernel360.trackyweb.drivehistory.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistory;

@Tag(name = "DriveHistory Api", description = "운행 기록 API")
public interface DriveHistoryApiDocs {

	@Operation(summary = "예약자 조회", description = "예약자 관련 운행 정보 조회")
	ApiResponse<List<RentDriveHistory>> findAllRentHistories(
		@RequestParam(required = false) String rentUuid
	);
}
