package kernel360.trackyconsumer.application.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.entity.LocationEntity;
import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackycore.core.domain.vo.GpsInfo;

public record CarOnOffRequest(
	String mdn,
	String gcd,

	EmulatorInfo emulatorInfo,

	GpsInfo gpsInfo,

	LocalDateTime onTime,

	LocalDateTime offTime
) {
	@JsonCreator
	public CarOnOffRequest(
		@JsonProperty String mdn,
		@JsonProperty String gcd,
		@JsonProperty String tid,
		@JsonProperty String mid,
		@JsonProperty String pv,
		@JsonProperty String did,
		@JsonProperty int lat,
		@JsonProperty int lon,
		@JsonProperty int ang,
		@JsonProperty int spd,
		@JsonProperty double sum,
		@JsonProperty @JsonFormat(pattern = "yyyyMMddHHmm") LocalDateTime onTime,
		@JsonProperty @JsonFormat(pattern = "yyyyMMddHHmm") LocalDateTime offTime
	) {
		this(mdn,
			gcd,
			EmulatorInfo.create(),
			GpsInfo.create(lat, lon, ang, spd, sum),
			onTime,
			offTime);
	}

	public LocationEntity toLocationEntity() {
		return LocationEntity.create(
			this.gpsInfo.getLon(),
			this.gpsInfo.getLat());
	}
}
