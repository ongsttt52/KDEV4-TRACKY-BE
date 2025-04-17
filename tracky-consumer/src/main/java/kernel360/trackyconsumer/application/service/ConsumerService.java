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

	@Async("taskExecutor")
	@Transactional
	public void receiveCycleInfo(GpsHistoryMessage request) {

		// 처리 로직
		List<CycleGpsRequest> cycleGpsRequestList = request.getCList();

		CarEntity car = carEntityRepository.findByMdn(request.getMdn());

		DriveEntity drive = driveEntityRepository.findByCarAndOtime(car, request.getOTime());

		// GPS 쪼개서 정보 저장
		for (int i = 0; i < request.getCCnt(); i++) {
			saveCycleInfo(drive, request.getOTime(), cycleGpsRequestList.get(i).getSum(), cycleGpsRequestList.get(i));
		}
	}

	private void saveCycleInfo(DriveEntity drive, LocalDateTime oTime, double sum, CycleGpsRequest cycleGpsRequest) {

		GpsHistoryEntity gpsHistoryEntity = cycleGpsRequest.toGpsHistoryEntity(drive, oTime, sum);

		gpsHistoryEntityRepository.save(gpsHistoryEntity);
	}

	@Transactional
	public void processOnMessage(CarOnOffRequest carOnOffRequest) {

		LocationEntity location = carOnOffRequest.toLocationEntity();
		locationEntityRepository.save(location);

		String mdn = carOnOffRequest.getMdn();
		CarEntity car = carEntityRepository.findByMdn(mdn);

		RentEntity rent = rentEntityRepository.findMyCarAndTime(car, carOnOffRequest.getOnTime());

		DriveEntity drive = DriveEntity.create(car, rent, location,
			carOnOffRequest.getOnTime());

		driveEntityRepository.save(drive);
	}

	@Transactional
	public void processOffMessage(CarOnOffRequest carOnOffRequest) {

		/** 시동 off시 다음 동작 수행
		 * 주행 종료 시간 update(Drive)
		 * 주행 별 거리 sum으로 update(Drive)
		 * 차량 주행 거리 += sum update(Car)
		 * 주행 종료 지점 update(Location)
		 **/
		CarEntity car = carEntityRepository.findByMdn(carOnOffRequest.getMdn());

		DriveEntity drive = driveEntityRepository.findByCarAndOtime(car, carOnOffRequest.getOnTime());
		drive.updateDistance(carOnOffRequest.getSum());
		drive.updateOffTime(carOnOffRequest.getOffTime());

		car.updateSum(drive.getDriveDistance());

		LocationEntity location = drive.getLocation();
		location.updateEndLocation(carOnOffRequest.getLat(), carOnOffRequest.getLon());
	}
}
