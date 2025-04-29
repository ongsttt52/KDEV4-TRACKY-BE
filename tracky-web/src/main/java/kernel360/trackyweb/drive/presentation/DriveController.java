package kernel360.trackyweb.drive.presentation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.drive.application.DriveService;
import kernel360.trackyweb.drive.application.dto.request.CarListRequest;
import kernel360.trackyweb.drive.application.dto.request.DriveListRequest;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import kernel360.trackyweb.drive.application.dto.response.DriveListResponse;
import kernel360.trackyweb.drive.domain.DriveHistory;
import kernel360.trackyweb.sign.infrastructure.security.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
@Slf4j
public class DriveController {

	private final DriveService driveService;

	//운향기록 페이지 왼편 차 리스트
	@GetMapping("/cars")
	public ApiResponse<List<CarListResponse>> getCarListBySearchFilter(
		@ModelAttribute CarListRequest carListSearchFilterRequest,
		@Schema(hidden = true) @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
		return driveService.getCarListBySearchFilter(memberPrincipal.bizUuid(), carListSearchFilterRequest);
	}

	@GetMapping()
	public ApiResponse<List<DriveListResponse>> getBySearchFilter(@ModelAttribute DriveListRequest driveListRequest) {
		return driveService.getDrivesBySearchFilter(driveListRequest);
	}

	//운행기록 상세
	@GetMapping("/{driveId}")
	public ApiResponse<DriveHistory> getDriveHistory(@PathVariable Long driveId) {
		DriveHistory history = driveService.getDriveHistory(driveId);
		return ApiResponse.success(history);
	}

	@GetMapping("/excel")
	public ResponseEntity<byte[]> exportExcel(
		@RequestParam String mdn) {

		byte[] res = driveService.exportExcel(mdn);

		// 파일명 생성 (기본 파일명 + 현재 날짜/시간)
		String simpleFileName = "car_list_" + LocalDate.now() + ".xlsx";

		// HTTP 응답 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(
			MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.setContentDispositionFormData("attachment", simpleFileName);

		// 엑셀 파일 응답
		return ResponseEntity
			.ok()
			.headers(headers)
			.body(res);
	}
}
