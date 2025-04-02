package kernel360.trackyemulator.application.service.generator;

import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.infrastructure.dto.CycleGpsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class CycleGpsDataGenerator {

    public CycleGpsRequest generate(EmulatorInstance instance) {
        int lastLat = instance.getCycleLastLat();
        int lastLon = instance.getCycleLastLon();
        int lastSpeed = instance.getCycleLastSpeed();
        int lastAng = instance.getCycleLastAng();

        int newSpeed = adjustSpeed(lastSpeed);  //마지막 속도 기반 새 속도 생성
        int newAng = adjustAngle(lastAng);  //마지막 방향 기반 새 방향 생성

        double distance = newSpeed;
        int[] newCoordinates = movePosition(lastLat, lastLon, newAng, distance);

        int newLat = newCoordinates[0];
        int newLon = newCoordinates[1];

        instance.updateCycleInfo(newLat, newLon, newSpeed, newAng, distance);

        return CycleGpsRequest.of(newLat, newLon, newAng, newSpeed, instance.getSum());
    }

    /*
    내부 계산 메서드들
     */

    //랜덤 스피드 - 현재 속도에서 5km/h 내에서 변화. 속도 최대 10~130으로 제한
    private int adjustSpeed(int currentSpeed) {
        int delta = ThreadLocalRandom.current().nextInt(-5, 6);
        int newSpeed = currentSpeed + delta;
        return Math.max(10, Math.min(newSpeed, 130));
    }

    //랜덤 방향 - 현재 각도에서 ±10도 범위 내에서 변화
    private int adjustAngle(int currentAngle) {
        int delta = ThreadLocalRandom.current().nextInt(-10, 11);
        int newAngle = (currentAngle + delta + 360) % 360;
        return newAngle;
    }

    //랜덤 위도 및 경도 - 방향과 이동 거리를 기준으로 새로운 위치 계산
    private int[] movePosition(int lat, int lon, int angle, double distanceMeter) {
        double earthRadius = 6378137;

        double dLat = (distanceMeter * Math.cos(Math.toRadians(angle))) / earthRadius;
        double dLon = (distanceMeter * Math.sin(Math.toRadians(angle))) / (earthRadius * Math.cos(Math.toRadians(lat / 1e6)));

        int newLat = lat + (int) (dLat * 1e6);
        int newLon = lon + (int) (dLon * 1e6);

        return new int[]{newLat, newLon};
    }
}