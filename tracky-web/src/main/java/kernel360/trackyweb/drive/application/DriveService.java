package kernel360.trackyweb.drive.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.drive.application.dto.request.DriveSearchFilterRequest;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import kernel360.trackyweb.drive.application.dto.response.DriveListResponse;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriveService {

	private final CarDomainProvider carDomainProvider;
	private final DriveDomainProvider driveDomainProvider;

	public ApiResponse<List<CarListResponse>> getCar() {
		List<CarListResponse> carList = carDomainProvider.getCar();

		return ApiResponse.success(carList);
	}

	public ApiResponse<List<DriveListResponse>> getBySearchFilter(
		DriveSearchFilterRequest driveSearchFilterRequest
	) {
		Page<DriveEntity> driveList = driveDomainProvider.searchByFilter(driveSearchFilterRequest);
		Page<DriveListResponse> responses = driveList.map(DriveListResponse::toResponse);
		PageResponse pageResponse = PageResponse.from(responses);

		return ApiResponse.success(responses.getContent(), pageResponse);
	}
}
