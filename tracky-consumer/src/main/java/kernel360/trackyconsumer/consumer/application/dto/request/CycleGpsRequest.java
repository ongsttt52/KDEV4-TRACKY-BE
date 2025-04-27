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
	public static CycleGpsRequest create(
			@JsonProperty("gcd") String gcd,
			@JsonProperty("gpsInfo") GpsInfo gpsInfo
	) {
		return new CycleGpsRequest(gcd, gpsInfo);
	}

	public GpsHistoryEntity toGpsHistoryEntity(DriveEntity drive, LocalDateTime oTime, double sum) {
		return new GpsHistoryEntity(drive, oTime, this.gcd, this.gpsInfo.getLat(), this.gpsInfo.getLon(),
			this.gpsInfo.getAng(), this.gpsInfo.getSpd(), sum);
	}
}
