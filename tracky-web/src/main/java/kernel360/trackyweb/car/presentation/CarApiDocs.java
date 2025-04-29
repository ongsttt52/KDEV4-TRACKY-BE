package kernel360.trackyweb.car.presentation;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.car.application.dto.request.CarCreateRequest;
import kernel360.trackyweb.car.application.dto.request.CarDeleteRequest;
import kernel360.trackyweb.car.application.dto.request.CarSearchByFilterRequest;
import kernel360.trackyweb.car.application.dto.request.CarUpdateRequest;
import kernel360.trackyweb.car.application.dto.response.CarDetailResponse;
import kernel360.trackyweb.car.application.dto.response.CarResponse;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;

@Tag(name = "Car API", description = "차량 관련 API")
public interface CarApiDocs {

	@Operation(summary = "mdn 중복 체크", description = "mdn 중복 체크")
	ApiResponse<Boolean> existsByMdn(@PathVariable String mdn);

	@Operation(summary = "mdn, status 필터링 검색", description = "mdn, status 필터링 차량 검색합니다")
	ApiResponse<List<CarResponse>> getAllBySearchFilter(
		@ModelAttribute CarSearchByFilterRequest carSearchByFilterRequest,
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal
	);

	@Operation(summary = "차량 MDN으로 상세 조회", description = "MDN 기준으로 차량 및 디바이스 상세 정보를 조회합니다.")
	ApiResponse<CarDetailResponse> searchOne(@PathVariable String mdn);

	@Operation(summary = "차량 신규 등록", description = "차량 신규 등록 API")
	ApiResponse<CarDetailResponse> create(@RequestBody CarCreateRequest carCreateRequest);

	@Operation(summary = "차량 정보 수정", description = "차량 정보를 수정하는 API")
	ApiResponse<CarDetailResponse> update(@RequestBody CarUpdateRequest carUpdateRequest);

	@Operation(summary = "차량 삭제", description = "차량 정보 삭제 API")
	ApiResponse<CarDetailResponse> delete(@RequestBody CarDeleteRequest carDeleteRequest);
}
