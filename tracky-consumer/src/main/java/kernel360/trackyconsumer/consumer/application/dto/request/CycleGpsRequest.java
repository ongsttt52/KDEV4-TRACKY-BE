package kernel360.trackyconsumer.consumer.application.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.vo.GpsInfo;

public record CycleGpsRequest(
	String gcd,
	GpsInfo gpsInfo
) {
	@JsonCreator
	public CycleGpsRequest(
		@JsonProperty String gcd,
		@JsonProperty int lat,
		@JsonProperty int lon,
		@JsonProperty int ang,
		@JsonProperty int spd,
		@JsonProperty double sum
	) {
		this(gcd, GpsInfo.create(lat, lon, ang, spd, sum));
	}

	public GpsHistoryEntity toGpsHistoryEntity(DriveEntity drive, LocalDateTime oTime, double sum) {
		return new GpsHistoryEntity(drive, oTime, this.gcd, this.gpsInfo.getLat(), this.gpsInfo.getLon(),
			this.gpsInfo.getAng(), this.gpsInfo.getSpd(), sum);
	}
}
