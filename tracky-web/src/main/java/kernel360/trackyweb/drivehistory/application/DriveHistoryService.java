package kernel360.trackyweb.drivehistory.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackyweb.drivehistory.domain.DriveHistoryDto;
import kernel360.trackyweb.drivehistory.domain.RentDriveHistoryDto;
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

	public List<RentDriveHistoryDto> getAllRentHistories() {
		return rentHistoryRepository.findAllRentHistories();
	}

	public List<DriveHistoryDto> getDriveHistoriesByDriveid(Long Id) {
		List<DriveEntity> drives = driveHistoryRepository.findAllById(Id);

		return drives.stream()
			.map(drive -> {
				Optional<GpsHistoryEntity> onGpsOpt = gpsHistoryRepository.findFirstByDriveOrderByDriveSeqAsc(drive);
				Optional<GpsHistoryEntity> offGpsOpt = gpsHistoryRepository.findFirstByDriveOrderByDriveSeqDesc(drive);

				int onLat = onGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int onLon = onGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				int offLat = offGpsOpt.map(GpsHistoryEntity::getLat).orElse(0);
				int offLon = offGpsOpt.map(GpsHistoryEntity::getLon).orElse(0);
				double sum = offGpsOpt.map(GpsHistoryEntity::getSum).orElse(0.0);

				List<GpsHistoryEntity> gpsList = gpsHistoryRepository.findByDrive(drive);
				List<DriveHistoryDto.Coordinate> coords = gpsList.stream()
					.map(
						gps -> new DriveHistoryDto.Coordinate(gps.getLat(), gps.getLon(), gps.getSpd(), gps.getOTime()))
					.collect(Collectors.toList());

				return new DriveHistoryDto(
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
}
