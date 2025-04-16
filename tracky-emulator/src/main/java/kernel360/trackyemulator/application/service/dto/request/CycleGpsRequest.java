package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

public record CycleGpsRequest(
	LocalDateTime oTime,
	long lat,
	long lon,
	int ang,
	int spd,
	double sum
) {
	public static CycleGpsRequest of(long lat, long lon, int ang, int spd, double sum) {
		return new CycleGpsRequest(
			LocalDateTime.now(), // 현재 시간 설정
			lat,
			lon,
			ang,
			spd,
			sum
		);
	}
}
