package kernel360.trackyweb.realtime.application.dto.response;

import java.time.LocalDateTime;

import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;

public record GpsDataResponse(
	LocalDateTime oTime,
	int lat,
	int lon,
	int ang,
	int spd
) {
	public static GpsDataResponse from(GpsHistoryEntity entity) {
		return new GpsDataResponse(
			entity.getOTime(),
			entity.getLat(),
			entity.getLon(),
			entity.getAng(),
			entity.getSpd()
		);
	}
}
