package kernel360trackybe.trackyhub.application.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.GpsInfo;

import java.time.LocalDateTime;

public record CycleGpsRequest(LocalDateTime oTime, String gcd, GpsInfo gpsInfo) {

	@JsonCreator
	public CycleGpsRequest(
		@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime oTime,
		@JsonProperty String gcd,
		@JsonProperty int lat,
		@JsonProperty int lon,
		@JsonProperty int ang,
		@JsonProperty int spd,
		@JsonProperty double sum
	) {
		this(oTime, gcd, GpsInfo.create(lat, lon, ang, spd, sum));
	}
}
