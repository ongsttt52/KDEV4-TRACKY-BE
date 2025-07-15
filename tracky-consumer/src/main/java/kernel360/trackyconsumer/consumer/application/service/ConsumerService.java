package kernel360.trackyconsumer.consumer.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import kernel360.trackyconsumer.consumer.application.dto.request.CarOnOffRequest;
import kernel360.trackyconsumer.consumer.application.dto.request.CycleGpsRequest;
import kernel360.trackyconsumer.consumer.application.dto.request.GpsHistoryMessage;
import kernel360.trackyconsumer.drive.domain.provider.DriveDomainProvider;
import kernel360.trackyconsumer.rent.domain.provider.RentDomainProvider;
import kernel360.trackyconsumer.timedistance.domain.provider.TimeDistanceDomainProvider;
import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.entity.LocationEntity;
import kernel360.trackycore.core.domain.entity.RentEntity;
import kernel360.trackycore.core.domain.entity.TimeDistanceEntity;
import kernel360.trackycore.core.domain.provider.CarProvider;
import kernel360.trackycore.core.domain.provider.GpsHistoryProvider;
import kernel360.trackycore.core.domain.provider.LocationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

	private final DriveDomainProvider driveProvider;
	private final CarProvider carProvider;
	private final LocationProvider locationProvider;
	private final GpsHistoryProvider gpsHistoryProvider;
	private final RentDomainProvider rentDomainProvider;
	private final TimeDistanceDomainProvider timeDistanceDomainProvider;

	@Transactional
	public void receiveCycleInfo(GpsHistoryMessage request) {
		List<CycleGpsRequest> cycleGpsRequestList = request.cList();

		CarEntity car = carProvider.findByMdn(request.mdn()); // 캐싱 도입
		DriveEntity drive = driveProvider.getDrive(car, request.oTime());

		drive.skipCount(removeOverDistance(cycleGpsRequestList));

		if (!cycleGpsRequestList.isEmpty()) {
			processTimeDistance(cycleGpsRequestList, car);
		}

		List<GpsHistoryEntity> gpsHistories = toGpsHistoryList(cycleGpsRequestList, drive);
		gpsHistoryProvider.saveAll(gpsHistories);
	}

	@Transactional
	public List<GpsHistoryEntity> receiveCycleInfo_bulk(GpsHistoryMessage request) {
		List<CycleGpsRequest> cycleGpsRequestList = request.cList();

		CarEntity car = carProvider.findByMdn(request.mdn()); // 캐싱 도입
		DriveEntity drive = driveProvider.getDrive(car, request.oTime());

		long prevMillis = System.currentTimeMillis();
		drive.skipCount(removeOverDistance(cycleGpsRequestList));
		long afterMillis = System.currentTimeMillis();
		log.info("receiveCycleInfo - skipCount 시간 측정 결과 : {}ms", afterMillis - prevMillis);

		if (!cycleGpsRequestList.isEmpty()) {
			processTimeDistance(cycleGpsRequestList, car);
		}

		return toGpsHistoryList(cycleGpsRequestList, drive);
	}

	public void saveAllGps(List<GpsHistoryEntity> gpsHistories) {
		gpsHistoryProvider.saveAll(gpsHistories);
	}

	@Transactional
	public void processOnMessage(CarOnOffRequest carOnOffRequest) {

		LocationEntity location = LocationEntity.create(carOnOffRequest.gpsInfo().getLon(),
			carOnOffRequest.gpsInfo().getLat());
		locationProvider.save(location);

		CarEntity car = carProvider.findByMdn(carOnOffRequest.mdn());
		car.updateStatusToRunning();

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
		car.off(drive.getDriveDistance(), carOnOffRequest.offTime());

		LocationEntity location = drive.getLocation();
		location.updateEndLocation(carOnOffRequest.gpsInfo().getLat(), carOnOffRequest.gpsInfo().getLon());
	}

	private List<GpsHistoryEntity> toGpsHistoryList(List<CycleGpsRequest> cycleGpsRequestList,
		DriveEntity drive) {

		return cycleGpsRequestList.stream()
			.map(request -> request.toGpsHistoryEntity(drive, request.gpsInfo().getSum()))
			.toList();
	}

	private int removeOverDistance(List<CycleGpsRequest> cycleGpsRequests) {

		int count = 0;
		for (int i = cycleGpsRequests.size() - 1; i >= 0; i--) {
			if (cycleGpsRequests.get(i).gpsInfo().getSum() > 80) {
				cycleGpsRequests.remove(i);
				count++;
			}
		}
		return count;
	}

	/**
	 * 주기 정보(60개)를 순회하며 TimeDistance를 저장한다.
	 * 만약 시간이 바뀌었다면 바뀐 시간으로 TimeDistance를 저장한다.(if (nowHour != newHour))
	 * DB 접근을 최소화하기 위해 움직이지 않은 경우엔 저장하지 않는다.(if (distance > 0.0))
	 */
	private void processTimeDistance(List<CycleGpsRequest> cycleGpsRequests, CarEntity car) {

		double distance = 0.0;
		int seconds = 1;
		LocalDate prevDate = cycleGpsRequests.get(0).oTime().toLocalDate();
		int prevHour = cycleGpsRequests.get(0).oTime().getHour();

		long prevMillis = System.currentTimeMillis();
		for (CycleGpsRequest cycleGpsRequest : cycleGpsRequests) {

			LocalDate currentDate = cycleGpsRequest.oTime().toLocalDate();
			int currentHour = cycleGpsRequest.oTime().getHour();

			if (prevHour != currentHour) {
				if (distance > 0.0)
					saveTimeDistance(prevDate, prevHour, car, distance, seconds);
				distance = 0.0;
				prevDate = currentDate;
				prevHour = currentHour;
			}

			distance += cycleGpsRequest.gpsInfo().getSum();
			seconds++;
		}
		long afterMillis = System.currentTimeMillis();
		log.info("processTimedistance - Hour 변경 시 저장 시간 측정 결과 : {}ms", afterMillis - prevMillis);

		prevMillis = System.currentTimeMillis();
		if (distance > 0.0)
			saveTimeDistance(prevDate, prevHour, car, distance, seconds);
		afterMillis = System.currentTimeMillis();
		log.info("processTimedistance - saveTimeDistance 시간 측정 결과 : {}ms", afterMillis - prevMillis);
	}

	private void saveTimeDistance(LocalDate date, int hour, CarEntity car, double totalDistance, int seconds) {

		Optional<TimeDistanceEntity> timeDistance = timeDistanceDomainProvider.getTimeDistance(date, hour, car);

		if (timeDistance.isPresent()) {
			timeDistance.get().updateDistance(totalDistance, seconds);
		} else {
			timeDistanceDomainProvider.save(
				TimeDistanceEntity.create(car, car.getBiz(), date, hour, totalDistance, seconds, 1L)
			);
		}
		// timeDistanceDomainProvider.getTimeDistance(date, hour, car)
		// 	.ifPresentOrElse(
		// 		timeDistance -> timeDistance.updateDistance(totalDistance, seconds),
		// 		() -> timeDistanceDomainProvider.save(
		// 			TimeDistanceEntity.create(car, car.getBiz(), date, hour, totalDistance, seconds)
		// 		)
		// 	);
	}
}

/*
// 해결책이 적용된 코드
private void processTimeDistance(List<CycleGpsRequest> cycleGpsRequests, CarEntity car) {

    // --- 1단계: 쇼핑 리스트 만들기 ---
    // 앞으로 필요한 모든 (날짜, 시간) 키를 미리 추출합니다.
    Set<String> timeKeys = cycleGpsRequests.stream()
        .map(req -> {
            LocalDate date = req.oTime().toLocalDate();
            int hour = req.oTime().getHour();
            return date.toString() + "-" + hour; // "2025-07-11-14" 형태의 키 생성
        })
        .collect(Collectors.toSet());

    // --- 2단계: 마트에 딱 한 번 가서 장보기 ---
    // 쇼핑 리스트(timeKeys)를 이용해 필요한 TimeDistance 데이터를 '한 번의 쿼리'로 모두 가져옵니다.
    // Key: "날짜-시간", Value: TimeDistanceEntity
    Map<String, TimeDistanceEntity> timeDistanceMap = timeDistanceDomainProvider
        .findAllByCarAndTimeKeys(car, timeKeys) // 이런 Repository 메서드를 새로 만들어야 합니다.

        .stream()
        .collect(Collectors.toMap(
            td -> td.getDate().toString() + "-" + td.getHour(), // Map의 Key
            td -> td // Map의 Value
        ));

    // ================== 이제 DB 조회는 끝났습니다. 모든 처리는 메모리(카트 안)에서 일어납니다. ==================

    double distance = 0.0;
    int seconds = 1;
    LocalDate prevDate = cycleGpsRequests.get(0).oTime().toLocalDate();
    int prevHour = cycleGpsRequests.get(0).oTime().getHour();

    // --- 3단계: 카트에 담아온 물건들로 요리하기 ---
    for (CycleGpsRequest cycleGpsRequest : cycleGpsRequests) {
        LocalDate currentDate = cycleGpsRequest.oTime().toLocalDate();
        int currentHour = cycleGpsRequest.oTime().getHour();

        if (prevHour != currentHour) {
            if (distance > 0.0) {
                // DB에 가지 않고, 내 카트(Map)에서 물건을 찾아 업데이트합니다.
                updateOrRegisterTimeDistance(timeDistanceMap, prevDate, prevHour, car, distance, seconds);
            }
            distance = 0.0;
            prevDate = currentDate;
            prevHour = currentHour;
        }

        distance += cycleGpsRequest.gpsInfo().getSum();
        seconds++;
    }

    if (distance > 0.0) {
        // 마지막 정산 역시 내 카트(Map)에서 처리합니다.
        updateOrRegisterTimeDistance(timeDistanceMap, prevDate, prevHour, car, distance, seconds);
    }

    // --- 4단계: (선택) 새로 만든 물건 저장하기 ---
    // JPA의 Dirty Checking 덕분에 Map에서 수정한 것들은 트랜잭션 종료 시 자동으로 UPDATE 됩니다.
    // 하지만 Map에 새로 추가된(기존에 없던) 데이터는 별도로 저장해줘야 할 수 있습니다. (로직에 따라 다름)
}

// DB에 가지 않고 Map을 사용하는 헬퍼 메서드
private void updateOrRegisterTimeDistance(Map<String, TimeDistanceEntity> map, LocalDate date, int hour, CarEntity car, double totalDistance, int seconds) {
    String key = date.toString() + "-" + hour;
    TimeDistanceEntity timeDistance = map.get(key); // DB 조회가 아닌, Map 조회!

    if (timeDistance != null) { // 카트(Map)에 데이터가 있으면
        timeDistance.updateDistance(totalDistance, seconds); // 메모리 객체 수정 (-> 나중에 자동 UPDATE 됨)
    } else { // 카트(Map)에 데이터가 없으면
        TimeDistanceEntity newEntity = TimeDistanceEntity.create(car, car.getBiz(), date, hour, totalDistance, seconds, 1L);
        // timeDistanceDomainProvider.save(newEntity); // 바로 저장하거나
        map.put(key, newEntity); // 다음번 조회를 위해 Map에도 추가하고, 마지막에 한 번에 저장할 수도 있음
    }
}

findAllByCarAndTimeKeys는 TimeDistanceRepository에 새로 만들어야 할 조회 메서드입니다.
필요한 모든 TimeDistance 데이터를 단 한 번의 DB 조회로 가져오는 것이 핵심
public interface TimeDistanceRepository extends JpaRepository<TimeDistanceEntity, Long> {

    // 아래 메서드 한 줄만 추가하면 됩니다.
    List<TimeDistanceEntity> findAllByCarAndDateInAndHourIn(CarEntity car, Collection<LocalDate> dates, Collection<Integer> hours);

}
SELECT * FROM time_distance WHERE car_id = ? AND date IN (?, ?, ...) AND hour IN (?, ?, ...)

findAllBy...: 여러 개의 결과를 반환하는 SELECT 쿼리를 의미합니다.

Car: WHERE car_id = ? 조건을 만듭니다. 메서드의 첫 번째 인자인 car 객체가 이 조건에 사용됩니다.

And: AND 키워드를 사용하여 여러 조건을 연결합니다.

DateIn: WHERE date IN (...) 조건을 만듭니다. 메서드의 두 번째 인자인 dates 컬렉션이 이 IN 절에 사용됩니다.

HourIn: WHERE hour IN (...) 조건을 만듭니다. 메서드의 세 번째 인자인 hours 컬렉션이 이 IN 절에 사용됩니다.
 */