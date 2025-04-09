package kernel360.trackyweb.drivehistory.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackyweb.drivehistory.domain.CarDriveHistory;
import kernel360.trackyweb.drivehistory.domain.DriveHistory;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistory;
import kernel360.trackyweb.drivehistory.infrastructure.repository.DriveHistoryRepository;
import kernel360.trackyweb.drivehistory.infrastructure.repository.GpsHistoryRepository;
import kernel360.trackyweb.drivehistory.infrastructure.repository.RentHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriveHistoryService {

	private final RentHistoryRepository rentHistoryRepository;
	private final DriveHistoryRepository driveHistoryRepository;
	private final GpsHistoryRepository gpsHistoryRepository;

	public List<RentDriveHistory> getAllRentHistories(String rentUuid) {
		// 기본 렌트 정보 (rentUuid, renterName, mdn, rentStime, rentEtime) 조회
		List<RentDriveHistory> rents = rentHistoryRepository.findAllRentHistories();

		if (rentUuid != null && !rentUuid.isEmpty()) {
			rents = rents.stream()
				.filter(r -> r.rentUuid().equals(rentUuid))
				.collect(Collectors.toList());
		}

		return rents.stream()
			.map(rentDto -> {
				// 각 렌트에 연결된 drive 리스트 조회
				List<DriveEntity> drives = driveHistoryRepository.findAllByRent_RentUuid(rentDto.rentUuid());

				// drive 리스트를 drivelistDto로 변환
				List<RentDriveHistory.DrivelistDto> driveList = drives.stream()
					.map(drive -> {
						Optional<GpsHistoryEntity> onGps = gpsHistoryRepository.findFirstByDriveIdOrderByDriveSeqAsc(
							drive.getId());
						Optional<GpsHistoryEntity> offGps = gpsHistoryRepository.findFirstByDriveIdOrderByDriveSeqDesc(
							drive.getId());

						return new RentDriveHistory.DrivelistDto(
							drive.getId(),
							onGps.map(GpsHistoryEntity::getLat).orElse(0),
							onGps.map(GpsHistoryEntity::getLon).orElse(0),
							offGps.map(GpsHistoryEntity::getLat).orElse(0),
							offGps.map(GpsHistoryEntity::getLon).orElse(0),
							offGps.map(GpsHistoryEntity::getSum).orElse(0.0),
							drive.getDriveOnTime(),
							drive.getDriveOffTime()
						);
					})
					.collect(Collectors.toList());

				// rent + drive 요약 리스트를 포함한 DTO 새로 생성해서 리턴
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

	public List<DriveHistory> getDriveHistoriesByDriveid(Long id) {
		List<DriveEntity> drives = driveHistoryRepository.findAllById(id);

		return drives.stream()
			.map(drive -> {
				Optional<GpsHistoryEntity> onGpsOpt = gpsHistoryRepository.findFirstByDriveIdOrderByDriveSeqAsc(
					drive.getId());
				Optional<GpsHistoryEntity> offGpsOpt = gpsHistoryRepository.findFirstByDriveIdOrderByDriveSeqDesc(
					drive.getId());

				int onLat = onGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int onLon = onGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				int offLat = offGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int offLon = offGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				double sum = offGpsOpt.map(GpsHistoryEntity::getSum).orElse(0.0);

				List<GpsHistoryEntity> gpsList = gpsHistoryRepository.findByDriveId(drive.getId());
				List<DriveHistory.Coordinate> coords = gpsList.stream()
					.map(
						gps -> new DriveHistory.Coordinate(gps.getLat(), gps.getLon(), gps.getSpd(), gps.getOTime()))
					.collect(Collectors.toList());

				return new DriveHistory(
					drive.getId(),
					onLat, onLon,
					offLat, offLon,
					sum,
					drive.getDriveOnTime(),
					drive.getDriveOffTime(),
					coords
				);
			})
			.collect(Collectors.toList());
	}

	/**
	 * 차량 mdn 검색으로 차량에 대한 운행정보 가져오기
	 * @param mdn
	 * @return 차량 별 운행기록 list
	 */
	public List<CarDriveHistory> getDriveHistoriesByCar(String mdn) {
		List<DriveEntity> drives = driveHistoryRepository.findAllByCar_Mdn(mdn);

		List<CarDriveHistory> results = drives.stream()
			.map(drive -> {
				Optional<GpsHistoryEntity> onGpsOpt = gpsHistoryRepository.findFirstByDriveIdOrderByDriveSeqAsc(
					drive.getId());
				Optional<GpsHistoryEntity> offGpsOpt = gpsHistoryRepository.findFirstByDriveIdOrderByDriveSeqDesc(
					drive.getId());

				int onLat = onGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int onLon = onGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				int offLat = offGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int offLon = offGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				double sum = offGpsOpt.map(GpsHistoryEntity::getSum).orElse(0.0);

				return CarDriveHistory.create(drive.getId(), onLat, onLon, offLat, offLon, sum, drive.getDriveOnTime(),
					drive.getDriveOffTime());
			})
			.collect(Collectors.toList());

		return results;
	}

}
