package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

import kernel360.trackycore.core.common.entity.vo.GpsInfo;

public record CycleGpsRequest(
	LocalDateTime oTime,
	GpsInfo gpsInfo,
	String gcd
) {
	public static CycleGpsRequest of(GpsInfo gpsInfo) {
		return new CycleGpsRequest(
			LocalDateTime.now(), // 현재 시간 설정
			gpsInfo,
			"A"
		);
	}
}
