package kernel360.trackyconsumer.application.dto;

import java.time.LocalDateTime;

import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.GpsHistoryEntity;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CycleGpsRequest {
	private int sec;    // 발생시간 '초'
	private String gcd;    // GPS 상태
	private GpsInfo gpsInfo;

	public GpsHistoryEntity toGpsHistoryEntity(DriveEntity drive, LocalDateTime oTime, double sum) {
		return new GpsHistoryEntity(drive, oTime, this.gcd, this.gpsInfo.getLat(), this.gpsInfo.getLon(),
			this.gpsInfo.getAng(), this.gpsInfo.getSpd(), sum);
	}

}
