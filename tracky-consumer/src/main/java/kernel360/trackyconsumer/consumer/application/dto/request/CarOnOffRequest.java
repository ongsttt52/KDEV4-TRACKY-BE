package kernel360.trackyconsumer.consumer.application.dto.request;

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

	public LocationEntity toLocationEntity() {
		return LocationEntity.create(
			this.gpsInfo.getLon(),
			this.gpsInfo.getLat());
	}
}
