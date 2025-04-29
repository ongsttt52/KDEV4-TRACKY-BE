package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackycore.core.domain.vo.GpsInfo;
import kernel360.trackyemulator.domain.EmulatorInstance;

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
		@JsonProperty("mdn") String mdn,
		@JsonProperty("gcd") String gcd,
		@JsonProperty("tid") String tid,
		@JsonProperty("mid") String mid,
		@JsonProperty("did") String did,
		@JsonProperty("pv") String pv,
		@JsonProperty("lat") int lat,
		@JsonProperty("lon") int lon,
		@JsonProperty("ang") int ang,
		@JsonProperty("spd") int spd,
		@JsonProperty("sum") double sum,
		@JsonProperty("onTime") @JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime onTime,
		@JsonProperty("offTime") @JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime offTime
	) {
		this(
			mdn,
			gcd,
			EmulatorInfo.create(tid, mid, did, pv),
			GpsInfo.create(lat, lon, ang, spd, sum),
			onTime,
			offTime
		);
	}

	/**
	 * 시동 ON DTO 생성
	 */
	public static CarOnOffRequest ofOn(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getMdn(),
			"A",
			car.getEmulatorInfo(),
			car.getCycleLastGpsInfo(),
			car.getCarOnTime(),
			null
		);
	}

	/**
	 * 시동 OFF DTO 생성
	 */
	public static CarOnOffRequest ofOff(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getMdn(),
			"A",
			car.getEmulatorInfo(),
			car.offGpsInfo(),
			car.getCarOnTime(),
			car.getCarOffTime()
		);
	}
}
