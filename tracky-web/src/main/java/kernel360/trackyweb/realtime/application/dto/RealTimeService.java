package kernel360.trackyweb.realtime.application.dto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.realtime.application.dto.request.RealTimeCarListRequest;
import kernel360.trackyweb.realtime.application.dto.response.LiveCarResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RealTimeService {

	private final DriveDomainProvider driveDomainProvider;

	public ApiResponse<List<LiveCarResponse>> getRunningCars(
		RealTimeCarListRequest realTimeCarListRequest
	) {

		Page<DriveEntity> cars = driveDomainProvider.findRunningDriveList(
			realTimeCarListRequest.search(),
			realTimeCarListRequest.toPageable()
		);

		Page<LiveCarResponse> driveResponses = cars.map(LiveCarResponse::from);
		PageResponse pageResponse = PageResponse.from(driveResponses);

		return ApiResponse.success(driveResponses.getContent(), pageResponse);
	}
}
