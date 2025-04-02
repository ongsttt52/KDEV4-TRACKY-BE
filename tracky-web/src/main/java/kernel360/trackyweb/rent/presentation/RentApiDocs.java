package kernel360.trackyweb.rent.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.rent.presentation.dto.RentRequest;
import kernel360.trackyweb.rent.presentation.dto.RentResponse;

@Tag(name = "Rent API", description = "렌트 관련 API")
public interface RentApiDocs {

	@Operation(summary = "대여 목록 전체 조회", description = "DB에 있는 모든 대여 리스트 조회")
	ApiResponse<List<RentResponse>> getAll();

	@Operation(summary = "대여 신규 등록", description = "대여 신규 등록 API")
	ApiResponse<RentResponse> create(@RequestBody RentRequest rentRequest);

	@Operation(summary = "대여 UUID로 단건 조회", description = "rent_uuid로 단일 대여 조회 API")
	ApiResponse<List<RentResponse>> searchByRentUuid(@PathVariable String rentUuid);
}
