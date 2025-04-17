package kernel360.trackyconsumer.application.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;

public record CycleGpsRequest(
	int sec,
	String gcd,
	GpsInfo gpsInfo
) {
	@JsonCreator
	public CycleGpsRequest(
		@JsonProperty int sec,
		@JsonProperty String gcd,
		@JsonProperty int lat,
		@JsonProperty int lon,
		@JsonProperty int ang,
		@JsonProperty int spd,
		@JsonProperty double sum
	) {
		this(sec, gcd, GpsInfo.create(lat, lon, ang, spd, sum));
	}

	public GpsHistoryEntity toGpsHistoryEntity(DriveEntity drive, LocalDateTime oTime, double sum) {
		return new GpsHistoryEntity(drive, oTime, this.gcd, this.gpsInfo.getLat(), this.gpsInfo.getLon(),
			this.gpsInfo.getAng(), this.gpsInfo.getSpd(), sum);
	}
}
