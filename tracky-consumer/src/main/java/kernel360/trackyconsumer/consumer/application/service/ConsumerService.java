package kernel360.trackyconsumer.consumer.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kernel360.trackycore.core.domain.entity.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kernel360.trackyconsumer.consumer.application.dto.request.CarOnOffRequest;
import kernel360.trackyconsumer.consumer.application.dto.request.CycleGpsRequest;
import kernel360.trackyconsumer.consumer.application.dto.request.GpsHistoryMessage;
import kernel360.trackyconsumer.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyconsumer.rent.domain.provider.RentDomainProvider;
import kernel360.trackyconsumer.timedistance.domain.provider.TimeDistanceProvider;
import kernel360.trackycore.core.domain.provider.CarProvider;
import kernel360.trackycore.core.domain.provider.GpsHistoryProvider;
import kernel360.trackycore.core.domain.provider.LocationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

	private final DriveDomainProvider driveProvider;
	private final CarProvider carProvider;
	private final LocationProvider locationProvider;
	private final GpsHistoryProvider gpsHistoryProvider;
	private final RentDomainProvider rentDomainProvider;
	private final TimeDistanceProvider timeDistanceProvider;

	@Async("taskExecutor")
	@Transactional
	public void receiveCycleInfo(GpsHistoryMessage request) {

		List<CycleGpsRequest> cycleGpsRequestList = request.cList();
		CarEntity car = carProvider.findByMdn(request.mdn());
		LocalDateTime oTime = request.oTime();
		DriveEntity drive = driveProvider.getDrive(car, oTime);

		processTimeDistance(cycleGpsRequestList, oTime, car);

		List<GpsHistoryEntity> gpsHistories = toGpsHistoryList(cycleGpsRequestList, drive, oTime);
		gpsHistoryProvider.saveAll(gpsHistories);
	}

	@Transactional
	public void processOnMessage(CarOnOffRequest carOnOffRequest) {

		LocationEntity location = LocationEntity.create(carOnOffRequest.gpsInfo().getLon(),
			carOnOffRequest.gpsInfo().getLat());
		locationProvider.save(location);

		CarEntity car = carProvider.findByMdn(carOnOffRequest.mdn());

		RentEntity rent = rentDomainProvider.getRent(car, carOnOffRequest.onTime());

		DriveEntity drive = DriveEntity.create(car, rent, location,
			carOnOffRequest.onTime());

		driveProvider.save(drive);
	}

	@Transactional
	public void processOffMessage(CarOnOffRequest carOnOffRequest) {

		CarEntity car = carProvider.findByMdn(carOnOffRequest.mdn());

		DriveEntity drive = driveProvider.getDrive(car, carOnOffRequest.onTime());
		drive.off(carOnOffRequest.gpsInfo().getSum(), carOnOffRequest.offTime());

		car.updateDistance(drive.getDriveDistance());

		LocationEntity location = drive.getLocation();
		location.updateEndLocation(carOnOffRequest.gpsInfo().getLat(), carOnOffRequest.gpsInfo().getLon());
	}

	private List<GpsHistoryEntity> toGpsHistoryList(List<CycleGpsRequest> cycleGpsRequestList,
													DriveEntity drive,
													LocalDateTime oTime) {
		List<GpsHistoryEntity> gpsHistories = new ArrayList<>();

		for (int i = 0; i < cycleGpsRequestList.size(); i++) {
			CycleGpsRequest cycleGpsRequest = cycleGpsRequestList.get(i);
			gpsHistories.add(cycleGpsRequest.toGpsHistoryEntity(drive, oTime.plusSeconds(i), cycleGpsRequest.gpsInfo().getSum()));
		}

		return gpsHistories;
	}

	/**
	 * 주기 정보(60개)를 순회하며 TimeDistance를 저장한다.
	 * 만약 시간이 바뀌었다면 바뀐 시간으로 TimeDistance를 저장한다.(if (nowHour != newHour))
	 * DB 접근을 최소화하기 위해 움직이지 않은 경우엔 저장하지 않는다.(if (distance > 0.0))
	 */
	private void processTimeDistance(List<CycleGpsRequest> cycleGpsRequests, LocalDateTime startTime, CarEntity car) {

		double distance = 0.0;

		LocalDate currentDate = startTime.toLocalDate();
		int currentHour = startTime.getHour();

		int sec = 0;
		for (CycleGpsRequest cycleGpsRequest : cycleGpsRequests) {

			LocalDateTime nextTime = startTime.plusSeconds(sec++);
			LocalDate nextDate = nextTime.toLocalDate();
			int nextHour = nextTime.getHour();

			if (currentHour != nextHour) {
				if (distance > 0.0) saveTimeDistance(currentDate, currentHour, car, distance);

				distance = 0.0;
				currentDate = nextDate;
				currentHour = nextHour;
			}
			distance += cycleGpsRequest.gpsInfo().getSum();
		}

		if (distance > 0.0) saveTimeDistance(currentDate, currentHour, car, distance);
	}

	private void saveTimeDistance(LocalDate date, int hour, CarEntity car, double totalDistance) {

		timeDistanceProvider.getTimeDistance(date, hour, car)
				.ifPresentOrElse(
						timeDistance -> timeDistance.updateDistance(totalDistance),
						() -> timeDistanceProvider.save(
								TimeDistanceEntity.create(car, car.getBiz(), date, hour, totalDistance)
						)
				);
	}
}
