package kernel360.trackyemulator.application.service.generator;

import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.presentation.dto.CycleGpsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class CycleDataGenerator {

    public CycleGpsRequest generate(EmulatorInstance instance) {
        long lastLat = instance.getCycleLastLat();
        long lastLon = instance.getCycleLastLon();
        int lastSpeed = instance.getCycleLastSpeed();
        int lastAng = instance.getCycleLastAng();

        int newSpeed = adjustSpeed(lastSpeed);
        int newAng = adjustAngle(lastAng);

        double distance = newSpeed;
        long[] newCoordinates = movePosition(lastLat, lastLon, newAng, distance);

        long newLat = newCoordinates[0];
        long newLon = newCoordinates[1];

        instance.addSum(distance);
        instance.setCycleLastInfo(newLat, newLon, newSpeed, newAng);

        return CycleGpsRequest.of(newLat, newLon, newAng, newSpeed, instance.getSum());
    }

    /*
    내부 계산 메서드들
     */

    //랜덤 스피드
    private int adjustSpeed(int currentSpeed) {
        int delta = ThreadLocalRandom.current().nextInt(-5, 6);
        int newSpeed = currentSpeed + delta;
        return Math.max(10, Math.min(newSpeed, 100));
    }

    //랜덤 방향
    private int adjustAngle(int currentAngle) {
        int delta = ThreadLocalRandom.current().nextInt(-10, 11);
        int newAngle = (currentAngle + delta + 360) % 360;
        return newAngle;
    }

    //랜덤 위도 및 경도
    private long[] movePosition(long lat, long lon, int angle, double distanceMeter) {
        double earthRadius = 6378137;

        double dLat = (distanceMeter * Math.cos(Math.toRadians(angle))) / earthRadius;
        double dLon = (distanceMeter * Math.sin(Math.toRadians(angle))) / (earthRadius * Math.cos(Math.toRadians(lat / 1e6)));

        long newLat = lat + (long) (dLat * 1e6);
        long newLon = lon + (long) (dLon * 1e6);

        return new long[]{newLat, newLon};
    }
}