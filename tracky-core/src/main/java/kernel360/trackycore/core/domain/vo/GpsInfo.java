package kernel360.trackycore.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GpsInfo {
	private final int lat;
	private final int lon;
	private final int ang;
	private final int spd;
	private final double sum;

	@JsonCreator
	private GpsInfo(
			@JsonProperty("lat") int lat,
			@JsonProperty("lon") int lon,
			@JsonProperty("ang") int ang,
			@JsonProperty("spd") int spd,
			@JsonProperty("sum") double sum
	) {
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}

	public static GpsInfo create(int lat, int lon, int ang, int spd, double sum) {
		return new GpsInfo(lat, lon, ang, spd, sum);
	}
}
