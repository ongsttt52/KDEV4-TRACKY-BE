package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.vo.GpsInfo;

public record CycleGpsRequest(
	LocalDateTime oTime,
	GpsInfo gpsInfo,
	String gcd
) {

	@JsonCreator
	public CycleGpsRequest(
		@JsonProperty("oTime") LocalDateTime oTime,
		@JsonProperty("lat") int lat,
		@JsonProperty("lon") int lon,
		@JsonProperty("ang") int ang,
		@JsonProperty("spd") int spd,
		@JsonProperty("sum") double sum,
		@JsonProperty("gcd") String gcd
	) {
		this(
			oTime,
			GpsInfo.create(lat, lon, ang, spd, sum),
			gcd
		);
	}

	public static CycleGpsRequest of(GpsInfo gpsInfo) {
		return new CycleGpsRequest(
			LocalDateTime.now(),
			gpsInfo,
			"A"
		);
	}
}
