package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import kernel360.trackycore.core.domain.vo.GpsInfo;

@JsonIgnoreProperties(value = {"gpsInfo"})
public record CycleGpsRequest(
	LocalDateTime oTime,
	GpsInfo gpsInfo,
	String gcd
) {

	@JsonCreator
	public CycleGpsRequest(
		@JsonProperty("oTime") @JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime oTime,
		@JsonProperty("lat") int lat,
		@JsonProperty("lon") int lon,
		@JsonProperty("ang") int ang,
		@JsonProperty("spd") int spd,
		@JsonProperty("sum") double sum,
		@JsonProperty("gcd") String gcd
	) {
		this(
			oTime,
			GpsInfo.create(lat, lon, ang, spd, sum),
			gcd
		);
	}

	public static CycleGpsRequest of(GpsInfo gpsInfo) {
		return new CycleGpsRequest(
			LocalDateTime.now(),
			gpsInfo,
			"A"
		);
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

	// oTime을 원하는 포맷으로 직렬화
	@JsonGetter("oTime")
	public String getFormattedOTime() {
		if (oTime != null) {
			return oTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		}
		return null;
	}
}
