package kernel360.trackyweb.drive.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.drive.application.dto.request.CarListRequest;
import kernel360.trackyweb.drive.application.dto.request.DriveListRequest;
import kernel360.trackyweb.drive.application.dto.request.DrivesExportRequest;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import kernel360.trackyweb.drive.application.dto.response.DriveListResponse;
import kernel360.trackyweb.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyweb.drive.domain.vo.DriveHistory;
import kernel360.trackyweb.drive.infrastructure.repository.util.DriveExcelGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriveService {

	private final CarDomainProvider carDomainProvider;
	private final DriveDomainProvider driveDomainProvider;
	private final DriveExcelGenerator driveExcelGenerator;

	/**
	 * 운행 기록을 조회 할 차량 조회
	 *
	 * @param bizUuid
	 * @param carListSearchFilterRequest
	 * @return
	 */
	@Transactional(readOnly = true)
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

	/**
	 * 차량에 대한 운행 기록 리스트
	 *
	 * @param driveListRequest
	 * @return
	 */
	@Transactional(readOnly = true)
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

	/**
	 * gps data를 포함한 단건 조회
	 *
	 * @param driveId
	 * @return
	 */
	@Transactional(readOnly = true)
	public DriveHistory getDriveHistory(
		Long driveId
	) {
		return driveDomainProvider.findByDriveId(driveId);
	}

	public byte[] exportExcel(String mdn) {

		List<DriveEntity> drives = driveDomainProvider.findByMdn(mdn);
		List<DrivesExportRequest> drivesExportRequests = DrivesExportRequest.from(drives);

		return driveExcelGenerator.generateExcel(drivesExportRequests);
	}
}


