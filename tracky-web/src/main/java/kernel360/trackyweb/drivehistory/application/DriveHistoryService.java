package kernel360.trackyweb.drivehistory.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackyweb.drivehistory.domain.CarDriveHistory;
import kernel360.trackyweb.drivehistory.domain.DriveHistory;
import kernel360.trackyweb.drivehistory.domain.GpsData;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistory;
import kernel360.trackyweb.drivehistory.infrastructure.repo.DriveHistoryRepository;
import kernel360.trackyweb.drivehistory.infrastructure.repo.GpsHistoryDomainRepository;
import kernel360.trackyweb.drivehistory.infrastructure.repo.RentHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriveHistoryService {

	private final RentHistoryRepository rentHistoryRepository;
	private final DriveHistoryRepository driveHistoryRepository;
	private final GpsHistoryDomainRepository gpsHistoryRepository;

	public List<RentDriveHistory> getAllRentHistories(String rentUuid) {
		// rentUuid가 null이거나 빈 문자열이면 전체 조회, 아니면 특정 UUID 검색
		List<RentDriveHistory> rents = rentHistoryRepository.findRentHistoriesByRentUuid(
				(rentUuid == null || rentUuid.isEmpty()) ? null : rentUuid
			).stream()
			.sorted(Comparator.comparing(RentDriveHistory::rentStime).reversed())
			.limit(10)
			.collect(Collectors.toList());

		return rents.stream()
			.map(rentDto -> {
				// 각 렌트에 연결된 drive 리스트 조회
				List<DriveEntity> drives = driveHistoryRepository.findAllByRent_RentUuid(rentDto.rentUuid());

				// drive 리스트를 drivelistDto로 변환
				List<RentDriveHistory.DrivelistDto> driveList = drives.stream()
					.map(drive -> {
						Optional<GpsHistoryEntity> onGps = gpsHistoryRepository.findFirstGpsByDriveId(drive.getId());
						Optional<GpsHistoryEntity> offGps = gpsHistoryRepository.findLastGpsByDriveId(drive.getId());

						return new RentDriveHistory.DrivelistDto(
							drive.getId(),
							onGps.map(GpsHistoryEntity::getLat).orElse(0),
							onGps.map(GpsHistoryEntity::getLon).orElse(0),
							offGps.map(GpsHistoryEntity::getLat).orElse(0),
							offGps.map(GpsHistoryEntity::getLon).orElse(0),
							drive.getDriveDistance(),
							drive.getDriveOnTime(),
							drive.getDriveOffTime()
						);
					})
					.collect(Collectors.toList());

				// rent + drive 요약 리스트를 포함한 DTO 리턴
				return new RentDriveHistory(
					rentDto.rentUuid(),
					rentDto.renterName(),
					rentDto.mdn(),
					rentDto.rentStime(),
					rentDto.rentEtime(),
					driveList
				);
			})
			.collect(Collectors.toList());
	}

	public DriveHistory getDriveHistoriesByDriveId(Long id) {
		DriveEntity drive = driveHistoryRepository.findById(id)
			.orElseThrow(() -> GlobalException.throwError(ErrorCode.RENT_NOT_FOUND));

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
			.collect(Collectors.toList());

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

	/**
	 * 차량 mdn 검색으로 차량에 대한 운행정보 가져오기
	 *
	 * @param mdn
	 * @return 차량 별 운행기록 list
	 */
	public List<CarDriveHistory> getDriveHistoriesByCar(String mdn) {
		List<DriveEntity> drives = driveHistoryRepository.findAllByCar_Mdn(mdn);
		List<CarDriveHistory> results = drives.stream()
			.map(drive -> {
				Optional<GpsHistoryEntity> onGpsOpt = gpsHistoryRepository.findFirstGpsByDriveId(
					drive.getId());
				Optional<GpsHistoryEntity> offGpsOpt = gpsHistoryRepository.findLastGpsByDriveId(
					drive.getId());

				int onLat = onGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int onLon = onGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				int offLat = offGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int offLon = offGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				double sum = drive.getDriveDistance();
				RentEntity rent = drive.getRent();

				return CarDriveHistory.create(drive.getId(), rent.getRentUuid(), mdn, onLat, onLon, offLat,
					offLon, sum,
					drive.getDriveOnTime(),
					drive.getDriveOffTime());
			})
			.collect(Collectors.toList());

		return results;
	}

}
