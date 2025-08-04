package kernel360.trackyconsumer.consumer.application.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
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

	public GpsHistoryEntity toGpsHistoryEntity(String mdn, double sum) {
		return new GpsHistoryEntity(mdn, this.oTime, this.gcd, this.gpsInfo.getLat(), this.gpsInfo.getLon(),
			this.gpsInfo.getAng(), this.gpsInfo.getSpd(), sum);
	}
}
