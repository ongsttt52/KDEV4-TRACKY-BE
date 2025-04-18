package kernel360.trackyweb.rent.presentation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.rent.application.dto.request.RentRequest;
import kernel360.trackyweb.rent.application.dto.response.RentResponse;

@Tag(name = "Rent API", description = "렌트 관련 API")
public interface RentApiDocs {

	@Operation(summary = "rent 등록시 select 할 차량 전체 목록", description = "rent 등록시 select 할 차량 전체 목록 list")
	ApiResponse<List<String>> getAllMdns();
	
	@Operation(summary = "rent uuid, status, date 필터링 검색", description = "rent uuid, status, date 필터링 검색")
	ApiResponse<List<RentResponse>> searchByFilter(
		@RequestParam String rentUuid,
		@RequestParam(required = false, name = "status") String rentStatus,
		@RequestParam(required = false, name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentDate,
		// yyyy-MM-dd
		Pageable pageable
	);

	@Operation(summary = "대여 UUID로 단건 조회", description = "rent_uuid로 단일 대여 조회 API")
	ApiResponse<RentResponse> searchOne(@PathVariable String rentUuid);

	@Operation(summary = "대여 신규 등록", description = "대여 신규 등록 API")
	ApiResponse<RentResponse> create(@RequestBody RentRequest rentRequest);

	@Operation(summary = "대여 정보 수정", description = "대여 정보를 수정하는 API")
	ApiResponse<RentResponse> update(@PathVariable String rentUuid, @RequestBody RentRequest rentRequest);

	@Operation(summary = "대여 삭제", description = "대여 정보 삭제 API")
	ApiResponse<String> delete(@PathVariable String rentUuid);
}
