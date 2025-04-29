package kernel360.trackyweb.dashboard.presentation;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.dashboard.application.dto.response.ReturnResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;

@Tag(name = "DashBoard Api", description = "대쉬보드 관련 API")
public interface DashBoardApiDocs {

	@Operation(summary = "지연된 반납 조회", description = "반납이 지연된 차량 리스트")
	ApiResponse<List<ReturnResponse>> getdelayedReturn(
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	);

/*	@Operation(summary = "차량 상태 통계 조회", description = "등록된 차량 상태 통계")
	ApiResponse<Map<String, Long>> getAllCarStatus();

	@Operation(summary = "대시 보드 통계 조회", description = "대시 보드 통계 ( ")
	ApiResponse<Statistics> getStatistics();*/
}
