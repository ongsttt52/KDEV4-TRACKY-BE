package kernel360.trackyweb.realtime.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.realtime.application.dto.request.RealTimeCarListRequest;
import kernel360.trackyweb.realtime.application.dto.response.GpsDataResponse;
import kernel360.trackyweb.realtime.application.dto.response.RunningCarDetailResponse;
import kernel360.trackyweb.realtime.application.dto.response.RunningCarResponse;
import kernel360.trackyweb.realtime.domain.provider.GpsHistoryDomainProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class RealTimeService {

	private final DriveDomainProvider driveDomainProvider;
	private final GpsHistoryDomainProvider gpsHistoryDomainProvider;

	@Transactional(readOnly = true)
	public ApiResponse<List<RunningCarResponse>> getRunningCars(
		RealTimeCarListRequest realTimeCarListRequest
	) {

		Page<DriveEntity> cars = driveDomainProvider.findRunningDriveList(
			realTimeCarListRequest.search(),
			realTimeCarListRequest.toPageable()
		);

		Page<RunningCarResponse> driveResponses = cars.map(RunningCarResponse::from);
		PageResponse pageResponse = PageResponse.from(driveResponses);

		return ApiResponse.success(driveResponses.getContent(), pageResponse);
	}

	@Transactional(readOnly = true)
	public ApiResponse<RunningCarDetailResponse> getRunningCarDetailById(Long id) {
		DriveEntity drive = driveDomainProvider.findRunningDriveById(id);

		return ApiResponse.success(RunningCarDetailResponse.from(drive));
	}

/*	@Transactional(readOnly = true)
	public GpsDataResponse getOneGps(Long id) {
		return gpsHistoryDomainProvider.getOneGpsByDriveId(id);
	}*/

	@Transactional(readOnly = true)
	public List<GpsDataResponse> getBeforeGpsPath(Long id, LocalDateTime nowTime) {
		return gpsHistoryDomainProvider.getGpsPathBeforeTime(id, nowTime)
			.stream()
			.map(GpsDataResponse::from)
			.toList();
	}

}
