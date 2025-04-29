package kernel360.trackyweb.realtime.application.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.entity.DriveEntity;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;
import kernel360.trackycore.core.domain.vo.GpsInfo;

public record CycleGpsRequest(
	LocalDateTime oTime,
	String gcd,
	GpsInfo gpsInfo
) {
	@JsonCreator
	public static CycleGpsRequest create(
		@JsonProperty @JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime oTime,
		@JsonProperty("gcd") String gcd,
		@JsonProperty("gpsInfo") GpsInfo gpsInfo
	) {
		return new CycleGpsRequest(oTime, gcd, gpsInfo);
	}

	public GpsHistoryEntity toGpsHistoryEntity(DriveEntity drive, double sum) {
		return new GpsHistoryEntity(drive, this.oTime, this.gcd, this.gpsInfo.getLat(), this.gpsInfo.getLon(),
			this.gpsInfo.getAng(), this.gpsInfo.getSpd(), sum);
	}
}
