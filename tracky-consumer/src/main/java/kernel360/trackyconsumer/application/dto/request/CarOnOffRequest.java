package kernel360.trackyconsumer.application.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.common.entity.LocationEntity;
import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;

// 필요한 다른 import 문들 (EmulatorInfo, GpsInfo, LocationEntity)

public record CarOnOffRequest(
	String mdn,
	String gcd,

	EmulatorInfo emulatorInfo,

	GpsInfo gpsInfo,

	LocalDateTime onTime,  // 시동 On 시간

	LocalDateTime offTime // 시동 Off 시간
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

	// 기존 메소드는 record 내부에 그대로 유지할 수 있습니다.
	public LocationEntity toLocationEntity() {
		return LocationEntity.create(
			this.gpsInfo.getLon(),
			this.gpsInfo.getLat());
	}

	// 참고: 만약 GpsInfo가 record이고 접근자가 lon(), lat() 이라면 아래처럼 사용합니다.
    /*
    public LocationEntity toLocationEntityAlternative() {
       return LocationEntity.create(
          this.gpsInfo().lon(), // 또는 gpsInfo().getLon() - GpsInfo 구현에 따라 다름
          this.gpsInfo().lat()); // 또는 gpsInfo().getLat()
    }
    */
}
