package kernel360.trackyweb.admin.search.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackyweb.admin.search.application.request.AdminCarListRequest;
import kernel360.trackyweb.admin.search.application.request.AdminCarSearchByFilterRequest;
import kernel360.trackyweb.admin.search.application.request.AdminRealTimeCarListRequest;
import kernel360.trackyweb.admin.search.application.request.AdminRentSearchByFilterRequest;
import kernel360.trackyweb.admin.search.application.response.AdminCarListResponse;
import kernel360.trackyweb.admin.search.application.response.AdminCarResponse;
import kernel360.trackyweb.admin.search.application.response.AdminRentResponse;
import kernel360.trackyweb.admin.search.application.response.AdminRunningCarResponse;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.realtime.application.dto.response.RunningCarResponse;
import kernel360.trackyweb.rent.domain.provider.RentDomainProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminSearchService {

	private final CarDomainProvider carDomainProvider;
	private final RentDomainProvider rentDomainProvider;
	private final DriveDomainProvider driveDomainProvider;


	@Transactional(readOnly = true)
	public ApiResponse<List<AdminCarResponse>> getCarsBySearchFilterAdmin(
		AdminCarSearchByFilterRequest adminCarSearchByFilterRequest
	) {

		Page<CarEntity> cars = carDomainProvider.searchCarByFilterAdmin(
			adminCarSearchByFilterRequest.bizSearch(),
			adminCarSearchByFilterRequest.search(),
			adminCarSearchByFilterRequest.status(),
			adminCarSearchByFilterRequest.carType(),
			adminCarSearchByFilterRequest.toPageable());

		Page<AdminCarResponse> carResponses = cars.map(AdminCarResponse::from);
		PageResponse pageResponse = PageResponse.from(carResponses);

		return ApiResponse.success(carResponses.getContent(), pageResponse);
	}

	@Transactional(readOnly = true)
	public ApiResponse<List<AdminRentResponse>> adminRentSearchRentByFilterAdmin(
		AdminRentSearchByFilterRequest adminRentSearchByFilterRequest
	) {

		Page<RentEntity> rents = rentDomainProvider.searchRentByFilterAdmin(adminRentSearchByFilterRequest);
		Page<AdminRentResponse> rentResponses = rents.map(AdminRentResponse::from);
		PageResponse pageResponse = PageResponse.from(rentResponses);
		return ApiResponse.success(rentResponses.getContent(), pageResponse);

	}

	@Transactional(readOnly = true)
	public ApiResponse<List<AdminCarListResponse>> getCarListBySearchFilterAdmin(
		AdminCarListRequest adminCarListRequest
	) {
		Page<CarEntity> cars = carDomainProvider.searchDriveCarByFilterAdmin(
			adminCarListRequest.bizSearch(),
			adminCarListRequest.search(),
			adminCarListRequest.toPageable());

		Page<AdminCarListResponse> carResponses = cars.map(AdminCarListResponse::from);
		PageResponse pageResponse = PageResponse.from(carResponses);

		return ApiResponse.success(carResponses.getContent(), pageResponse);
	}

	@Transactional(readOnly = true)
	public ApiResponse<List<AdminRunningCarResponse>> getRunningCarsAdmin(
		AdminRealTimeCarListRequest adminRealTimeCarListRequest
	) {

		Page<DriveEntity> cars = driveDomainProvider.findRunningDriveListAdmin(
			adminRealTimeCarListRequest.bizSearch(),
			adminRealTimeCarListRequest.search(),
			adminRealTimeCarListRequest.toPageable()
		);

		Page<AdminRunningCarResponse> driveResponses = cars.map(AdminRunningCarResponse::from);
		PageResponse pageResponse = PageResponse.from(driveResponses);

		return ApiResponse.success(driveResponses.getContent(), pageResponse);
	}
}
