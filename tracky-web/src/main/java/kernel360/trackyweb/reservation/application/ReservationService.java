package kernel360.trackyweb.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.LocationEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackyweb.reservation.infrastructure.repo.ReservationDriveRepository;
import kernel360.trackyweb.reservation.infrastructure.repo.ReservationRentRepository;
import kernel360.trackyweb.reservation.presentation.dto.CarResponse;
import kernel360.trackyweb.reservation.presentation.dto.ReservationDrivingsResponse;
import kernel360.trackyweb.reservation.presentation.dto.ReservationResponse;
import kernel360.trackyweb.reservation.presentation.dto.ReservationWithCarResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRentRepository rentRepository;
	private final ReservationDriveRepository driveRepository;

	@Transactional(readOnly = true)
	public ApiResponse<ReservationWithCarResponse> searchRentWithCarByUuid(String uuid) {
		RentEntity rentEntity = rentRepository.findByRentUuid(uuid);    //렌트 테이블 매핑
		CarEntity carEntity = rentEntity.getCar();    //차량 테이블 매핑

		ReservationResponse reservationDto = ReservationResponse.fromEntity(rentEntity);
		CarResponse carDto = CarResponse.fromEntity(carEntity);

		return ApiResponse.success(ReservationWithCarResponse.of(reservationDto, carDto));
	}

	@Transactional(readOnly = true)
	public ApiResponse<List<ReservationDrivingsResponse>> searchDrivingsByUuid(String uuid) {
		List<DriveEntity> driveEntities = driveRepository.findAllByRent_RentUuid(uuid);

		List<ReservationDrivingsResponse> result = driveEntities.stream()
			.map(drive -> {
				LocationEntity location = drive.getLocation();
				return ReservationDrivingsResponse.fromEntity(drive, location);
			})
			.toList();
		return ApiResponse.success(result);
	}

}
