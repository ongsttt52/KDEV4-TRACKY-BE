package kernel360.trackyweb.admin.search.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.admin.search.application.AdminSearchService;
import kernel360.trackyweb.admin.search.application.request.AdminCarListRequest;
import kernel360.trackyweb.admin.search.application.request.AdminCarSearchByFilterRequest;
import kernel360.trackyweb.admin.search.application.request.AdminRealTimeCarListRequest;
import kernel360.trackyweb.admin.search.application.request.AdminRentSearchByFilterRequest;
import kernel360.trackyweb.admin.search.application.response.AdminCarListResponse;
import kernel360.trackyweb.admin.search.application.response.AdminCarResponse;
import kernel360.trackyweb.admin.search.application.response.AdminRentResponse;
import kernel360.trackyweb.admin.search.application.response.AdminRunningCarResponse;
import kernel360.trackyweb.realtime.application.dto.request.RealTimeCarListRequest;
import kernel360.trackyweb.realtime.application.dto.response.RunningCarResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminSearchController {

	private final AdminSearchService adminSearchService;

	@GetMapping("/cars")
	public ApiResponse<List<AdminCarResponse>> getCarsBySearchFilterAdmin(
		@ModelAttribute AdminCarSearchByFilterRequest adminCarSearchByFilterRequest
	) {
		return adminSearchService.getCarsBySearchFilterAdmin(adminCarSearchByFilterRequest);
	}
	
	@GetMapping("/rents")
	public ApiResponse<List<AdminRentResponse>> adminRentSearchRentByFilterAdmin(
		@ModelAttribute AdminRentSearchByFilterRequest adminRentSearchByFilterRequest
	) {
		return adminSearchService.adminRentSearchRentByFilterAdmin(adminRentSearchByFilterRequest);
	}

	@GetMapping("/drives/cars")
	public ApiResponse<List<AdminCarListResponse>> getCarListBySearchFilterAdmin(
		@ModelAttribute AdminCarListRequest adminCarListRequest
	) {
		return adminSearchService.getCarListBySearchFilterAdmin(adminCarListRequest);
	}

	@GetMapping("/realtime")
	public ApiResponse<List<AdminRunningCarResponse>> getRunningCarsAdmin(
		@ModelAttribute AdminRealTimeCarListRequest realTimeCarListRequest
	) {
		return adminSearchService.getRunningCarsAdmin(realTimeCarListRequest);
	}

}
