package kernel360.trackyweb.drive.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.provider.DriveProvider;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.drive.application.dto.request.CarListRequest;
import kernel360.trackyweb.drive.application.dto.request.DriveListRequest;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import kernel360.trackyweb.drive.application.dto.response.DriveListResponse;
import kernel360.trackyweb.drive.domain.DriveHistory;
import kernel360.trackyweb.drive.domain.GpsData;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.drive.infrastructure.repository.GpsHistoryDomainRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriveService {

	private final CarDomainProvider carDomainProvider;
	private final GpsHistoryDomainRepository gpsHistoryRepository;        // 추후에 분리 및 querydsl 작업해야함
	private final DriveProvider driveProvider;
	private final DriveDomainProvider driveDomainProvider;

	public ApiResponse<List<CarListResponse>> getCarListBySearchFilter(String bizUuid,
		CarListRequest carListSearchFilterRequest
	) {
		Page<CarEntity> cars = carDomainProvider.searchDriveCarByFilter(
			bizUuid,
			carListSearchFilterRequest.search(),
			carListSearchFilterRequest.toPageable());

		Page<CarListResponse> carResponses = cars.map(CarListResponse::from);
		PageResponse pageResponse = PageResponse.from(carResponses);

		return ApiResponse.success(carResponses.getContent(), pageResponse);

	}

	public ApiResponse<List<DriveListResponse>> getDrivesBySearchFilter(
		DriveListRequest driveListRequest
	) {
		Page<DriveEntity> driveList = driveDomainProvider.searchDrivesByFilter(
			driveListRequest.search(),
			driveListRequest.mdn(),
			driveListRequest.startDate(),
			driveListRequest.endDate(),
			driveListRequest.toPageable());

		Page<DriveListResponse> driveListResponses = driveList.map(DriveListResponse::toResponse);
		PageResponse pageResponse = PageResponse.from(driveListResponses);

		return ApiResponse.success(driveListResponses.getContent(), pageResponse);
	}

	// 추후에 분리 및 querydsl 작업해야함
	public DriveHistory getDriveHistroy(
		Long driveId
	) {
		DriveEntity drive = driveProvider.findById(driveId);

		Optional<GpsHistoryEntity> onGpsOpt = gpsHistoryRepository.findFirstGpsByDriveId(
			drive.getId());
		Optional<GpsHistoryEntity> offGpsOpt = gpsHistoryRepository.findLastGpsByDriveId(
			drive.getId());

		int onLat = onGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
		int onLon = onGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
		int offLat = offGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
		int offLon = offGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
		RentEntity rent = drive.getRent();

		List<GpsHistoryEntity> gpsList = gpsHistoryRepository.findByDriveId(drive.getId());
		List<GpsData> gpsDataList = gpsList.stream()
			.map(
				gps -> GpsData.create(gps.getLat(), gps.getLon(), gps.getSpd(), gps.getOTime()))
			.toList();

		return DriveHistory.create(
			drive.getId(),
			rent.getRentUuid(),
			drive.getCar().getMdn(),
			onLat, onLon,
			offLat, offLon,
			drive.getDriveDistance(),
			rent.getRenterName(),
			rent.getRenterPhone(),
			rent.getRentStatus(),
			rent.getPurpose(),
			drive.getDriveOnTime(),
			drive.getDriveOffTime(),
			rent.getRentStime(),
			rent.getRentEtime(),
			gpsDataList
		);
	}
}


