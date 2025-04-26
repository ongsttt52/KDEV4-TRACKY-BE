package kernel360.trackyconsumer.consumer.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kernel360.trackyconsumer.consumer.application.dto.GpsProcessResult;
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

		GpsProcessResult processResult = processGpsRequests(cycleGpsRequestList, drive, oTime);

		saveTimeDistance(oTime.toLocalDate(), oTime.getHour(), car, processResult.totalDistance());
		gpsHistoryProvider.saveAll(processResult.gpsHistoryList());
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

	private GpsProcessResult processGpsRequests(List<CycleGpsRequest> cycleGpsRequestList, DriveEntity drive, LocalDateTime oTime) {
		double totalDistance = 0.0;
		List<GpsHistoryEntity> gpsHistoryList = new ArrayList<>();

		for (CycleGpsRequest cycleRequest : cycleGpsRequestList) {
			totalDistance += cycleRequest.gpsInfo().getSum();
			gpsHistoryList.add(cycleRequest.toGpsHistoryEntity(drive, oTime, cycleRequest.gpsInfo().getSum()));
		}

		return GpsProcessResult.of(gpsHistoryList, totalDistance);
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
