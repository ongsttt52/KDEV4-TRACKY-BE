package kernel360.trackyweb.dashboard.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.domain.Statistics;
import kernel360.trackyweb.dashboard.presentation.dto.RentDashboardDto;

@Tag(name = "DashBoard Api", description = "대쉬보드 관련 API")
public interface DashBoardApiDocs {

	@Operation(summary = "예약 현황 조회", description = "어제/오늘/내일 선택된 날짜에 해당하는 예약 현황을 조회합니다.")
	ApiResponse<List<RentDashboardDto>> findRents(
		@RequestParam(name = "date", required = false, defaultValue = "today") String date
	);

	@Operation(summary = "차량 상태 통계 조회", description = "등록된 차량 상태 통계")
	ApiResponse<Map<String, Long>> getAllCarStatus();

	@Operation(summary = "대시 보드 통계 조회", description = "대시 보드 통계 ( ")
	ApiResponse<Statistics> getStatistics();
}
