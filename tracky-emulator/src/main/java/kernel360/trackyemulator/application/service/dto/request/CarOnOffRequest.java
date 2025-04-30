package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.EmulatorInfo;
import kernel360.trackycore.core.domain.vo.GpsInfo;
import kernel360.trackyemulator.domain.EmulatorInstance;

@JsonIgnoreProperties(value = {"emulatorInfo", "gpsInfo"})
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

	// EmulatorInfo의 필드들을 평면화하기 위한 JsonGetter 메서드들
	@JsonGetter("tid")
	public String getTid() {
		return emulatorInfo.getTid();
	}

	@JsonGetter("mid")
	public String getMid() {
		return emulatorInfo.getMid();
	}

	@JsonGetter("did")
	public String getDid() {
		return emulatorInfo.getDid();
	}

	@JsonGetter("pv")
	public String getPv() {
		return emulatorInfo.getPv();
	}

	// GpsInfo의 필드들을 평면화하기 위한 JsonGetter 메서드들
	@JsonGetter("lat")
	public int getLat() {
		return gpsInfo.getLat();
	}

	@JsonGetter("lon")
	public int getLon() {
		return gpsInfo.getLon();
	}

	@JsonGetter("ang")
	public int getAng() {
		return gpsInfo.getAng();
	}

	@JsonGetter("spd")
	public int getSpd() {
		return gpsInfo.getSpd();
	}

	@JsonGetter("sum")
	public double getSum() {
		return gpsInfo.getSum();
	}

	// onTime을 원하는 포맷으로 직렬화
	@JsonGetter("onTime")
	public String getFormattedOnTime() {
		if (onTime != null) {
			return onTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		}
		return null;
	}

	// offTime을 원하는 포맷으로 직렬화
	@JsonGetter("offTime")
	public String getFormattedOffTime() {
		if (offTime != null) {
			return offTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		}
		return null;
	}
}
