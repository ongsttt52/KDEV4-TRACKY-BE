package kernel360.trackyconsumer.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackyconsumer.application.dto.CarOnOffRequest;
import kernel360.trackyconsumer.application.dto.CycleGpsRequest;
import kernel360.trackyconsumer.application.dto.GpsHistoryMessage;
import kernel360.trackyconsumer.infrastructure.repository.CarEntityRepository;
import kernel360.trackyconsumer.infrastructure.repository.DriveEntityRepository;
import kernel360.trackyconsumer.infrastructure.repository.GpsHistoryEntityRepository;
import kernel360.trackyconsumer.infrastructure.repository.LocationEntityRepository;
import kernel360.trackyconsumer.infrastructure.repository.RentEntityRepository;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.common.entity.LocationEntity;
import kernel360.trackycore.core.common.entity.RentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

	private final DriveEntityRepository driveEntityRepository;
	private final GpsHistoryEntityRepository gpsHistoryEntityRepository;
	private final LocationEntityRepository locationEntityRepository;
	private final RentEntityRepository rentEntityRepository;
	private final CarEntityRepository carEntityRepository;

	@Async
	@Transactional
	public void receiveCycleInfo(GpsHistoryMessage request) {

		// 처리 로직
		List<CycleGpsRequest> cycleGpsRequestList = request.getCList();

		DriveEntity drive = driveEntityRepository.findByMdnAndOtime(request.getMdn(), request.getOTime());

		// GPS 쪼개서 정보 저장
		for (int i = 0; i < request.getCCnt(); i++) {
			saveCycleInfo(drive, request.getOTime(), cycleGpsRequestList.get(i).getSum(), cycleGpsRequestList.get(i));
		}
	}

	private void saveCycleInfo(DriveEntity drive, LocalDateTime oTime, double sum, CycleGpsRequest cycleGpsRequest) {
		// public void saveGpsMessage(LocalDateTime oTime, double sum, CycleGpsRequest cycleGpsRequest) {

		long maxSeq = gpsHistoryEntityRepository.findMaxSeqByDrive(drive);
		GpsHistoryEntity gpsHistoryEntity = cycleGpsRequest.toGpsHistoryEntity(maxSeq + 1, drive, oTime, sum);

		gpsHistoryEntityRepository.save(gpsHistoryEntity);

	}

	@Transactional
	public void processOnMessage(CarOnOffRequest carOnOffRequest) {

		LocationEntity location = carOnOffRequest.toLocationEntity();
		locationEntityRepository.save(location);

		String mdn = carOnOffRequest.getMdn();
		CarEntity car = carEntityRepository.findByMdn(mdn);

		RentEntity rent = rentEntityRepository.findMyMdnAndTime(mdn, carOnOffRequest.getOnTime());

		DriveEntity drive = DriveEntity.create(mdn, rent.getRentUuid(), car.getDevice().getId(), location,
			carOnOffRequest.getOnTime());

		// DriveEntity drive = DriveEntity.create("temp1234", "some_uuid", 1, location,
		// 	carOnOffRequest.getOnTime());

		driveRepository.save(drive);
	}

	@Transactional
	public void processOffMessage(CarOnOffRequest carOnOffRequest) {

		/** 시동 off시 다음 동작 수행
		 * 주행 종료 시간 update(Drive)
		 * 주행 별 거리 sum으로 update(Drive)
		 * 차량 주행 거리 += sum update(Car)
		 * 주행 종료 지점 update(Location)
		 **/
		log.info("drive 전 : {}");

		DriveEntity drive = driveRepository.findByMdnAndOtime(carOnOffRequest.getMdn(), carOnOffRequest.getOnTime());
		drive.updateDistance(carOnOffRequest.getSum());
		drive.updateOffTime(carOnOffRequest.getOffTime());

		CarEntity car = carEntityRepository.findByMdn(carOnOffRequest.getMdn());
		car.updateSum(drive.getDriveDistance());

		LocationEntity location = drive.getLocation();
		location.updateEndLocation(carOnOffRequest.getLat(), carOnOffRequest.getLon());
	}
}
