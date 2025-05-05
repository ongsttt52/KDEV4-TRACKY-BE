package kernel360trackybe.trackyhub.hub.application.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.GpsInfo;

public record CycleGpsRequest(LocalDateTime oTime, String gcd, GpsInfo gpsInfo) {

	@JsonCreator
	public CycleGpsRequest(
		@JsonProperty @JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime oTime,
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
