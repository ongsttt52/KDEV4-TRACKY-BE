package kernel360.trackyconsumer.application.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DriveEntity;
import kernel360.trackycore.core.common.entity.LocationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarOnOffRequest {
	private String mdn;           // 차량 식별 key
	private String tid;           // 차량관제 터미널 ID
	private String mid;           // 제조사 ID
	private String pv;            // 패킷 버전
	private String did;           // 디바이스 ID

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime onTime;  // 시동 On 시간

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime offTime; // 시동 Off 시간

	private String gcd;           // GPS 상태
	private int lat;             // GPS 위도
	private int lon;             // GPS 경도
	private int ang;           // 방향
	private int spd;           // 속도(km/h)
	private double sum;              // 누적 주행 거리(m)

	public LocationEntity toLocationEntity() {

		return LocationEntity.create(
			"시작 위치", // 이거 구해야하나? 프론트에서 하는거지?
			this.lon,
			this.lat);
	}
}
