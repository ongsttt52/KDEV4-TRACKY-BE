package kernel360.trackyemulator.application.service.generator;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.entity.vo.GpsInfo;
import kernel360.trackyemulator.application.service.dto.request.CycleGpsRequest;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CycleGpsDataGenerator {

	public CycleGpsRequest generate(EmulatorInstance instance) {
		// 위/경도 double 변환
		double lastLat = instance.getCycleLastGpsInfo().getLat() / 1_000_000.0;
		double lastLon = instance.getCycleLastGpsInfo().getLon() / 1_000_000.0;
		int lastSpeed = instance.getCycleLastGpsInfo().getSpd();
		int fixedAngle = instance.getCycleLastGpsInfo().getAng(); // 고정된 방향 (0~359도)

		// 속도 기준 이동 거리 계산 (m/s)
		int newSpeed = adjustSpeed(lastSpeed);
		double distance = (newSpeed * 1000.0) / 3600.0;

		// 거리 + 방향 → 정확한 위도/경도 계산
		double[] newCoordinates = movePosition(lastLat, lastLon, fixedAngle, distance);
		int newLat = (int)Math.round(newCoordinates[0] * 1_000_000);
		int newLon = (int)Math.round(newCoordinates[1] * 1_000_000);

		GpsInfo newGpsInfo = GpsInfo.create(newLat, newLon, fixedAngle, newSpeed, distance);

		// 상태 업데이트
		instance.updateCycleInfo(newGpsInfo);

		// 결과 생성
		return CycleGpsRequest.of(newGpsInfo);
	}

	private int adjustSpeed(int currentSpeed) {
		int delta = ThreadLocalRandom.current().nextInt(-5, 6);
		int newSpeed = currentSpeed + delta;
		return Math.max(30, Math.min(newSpeed, 130));
	}

	// 정확한 위도/경도 이동 계산
	private double[] movePosition(double lat, double lon, int angleDeg, double distanceMeter) {
		double earthRadius = 6371e3; // m
		double angleRad = Math.toRadians(angleDeg);

		// 위도/경도 변화량 계산 (라디안 단위)
		double deltaLat = distanceMeter * Math.cos(angleRad) / earthRadius;
		double deltaLon = distanceMeter * Math.sin(angleRad) / (earthRadius * Math.cos(Math.toRadians(lat)));

		// 이동 후 위도/경도 (도 단위로 변환)
		double newLat = lat + Math.toDegrees(deltaLat);
		double newLon = lon + Math.toDegrees(deltaLon);

		return new double[] {newLat, newLon};
	}
}
