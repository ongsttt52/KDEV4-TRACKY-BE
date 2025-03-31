package kernel360.trackyweb.car.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.car.presentation.dto.CarDetailResponse;
import kernel360.trackyweb.car.presentation.dto.CarRequest;
import kernel360.trackyweb.car.presentation.dto.CarResponse;

@Tag(name = "Car API", description = "차량 관련 API")
public interface CarApiDocs {

	@Operation(summary = "전체 차량 목록 조회", description = "모든 차량을 조회합니다.")
	ApiResponse<List<CarResponse>> getAll();

	@Operation(summary = "MDN으로 차량 검색", description = "MDN 키워드로 차량을 검색합니다.")
	ApiResponse<List<CarResponse>> searchByMdn(@RequestParam String mdn);

	@Operation(summary = "차량 ID로 단건 조회", description = "ID를 기준으로 차량을 조회합니다.")
	ApiResponse<CarResponse> searchById(@PathVariable Long id);

	@Operation(summary = "차량 ID로 상세 조회", description = "ID를 기준으로 차량 및 디바이스 상세 정보를 조회합니다.")
	ApiResponse<CarDetailResponse> searchDetailById(@PathVariable Long id);

	@Operation(summary = "차량 신규 등록", description = "차량 신규 등록 API")
	ApiResponse<CarDetailResponse> create(@RequestBody CarRequest carRequest);

	@Operation(summary = "차량 정보 수정", description = "차량 정보를 수정하는 API")
	ApiResponse<CarDetailResponse> update(@PathVariable Long id, @RequestBody CarRequest carRequest);

	@Operation(summary = "차량 삭제", description = "차량 정보 삭제 API")
	ApiResponse<String> delete(@PathVariable Long id);
}
