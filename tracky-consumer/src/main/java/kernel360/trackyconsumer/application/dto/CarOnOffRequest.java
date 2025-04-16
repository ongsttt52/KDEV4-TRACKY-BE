package kernel360.trackyconsumer.application.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel360.trackycore.core.common.entity.LocationEntity;
import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarOnOffRequest {
	private String mdn;
	private String gcd;
	private EmulatorInfo emulatorInfo;
	private GpsInfo gpsInfo;

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime onTime;  // 시동 On 시간

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime offTime; // 시동 Off 시간

	public LocationEntity toLocationEntity() {

		return LocationEntity.create(
			this.gpsInfo.getLon(),
			this.gpsInfo.getLat());
	}
}
