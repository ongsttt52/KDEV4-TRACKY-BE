package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackyemulator.domain.EmulatorInstance;
import lombok.Getter;

@Getter
public class CarOnOffRequest {

	private EmulatorInfo emulatorInfo;

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime onTime;
	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime offTime;

	private String gcd;
	private int lat;
	private int lon;
	private int ang;
	private int spd;
	private double sum;

	private CarOnOffRequest(EmulatorInfo emulatorInfo, LocalDateTime onTime, LocalDateTime offTime, String gcd, int lat,
		int lon, int ang, int spd, double sum) {
		this.emulatorInfo = emulatorInfo;
		this.onTime = onTime;
		this.offTime = offTime;
		this.gcd = gcd;
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}

	/**
	 * 시동 ON DTO 생성
	 */
	public static CarOnOffRequest ofOn(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getEmulatorInfo(),
			car.getCarOnTime(),
			null,               // 시동 ON시 offTime은 null
			"A",
			car.getCycleLastLat(),
			car.getCycleLastLon(),
			0,
			0,
			0
		);
	}

	/**
	 * 시동 OFF DTO 생성 (EmulatorInstance 상태 기반)
	 */
	public static CarOnOffRequest ofOff(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getEmulatorInfo(),
			car.getCarOnTime(),
			car.getCarOffTime(),
			"A",
			car.getCycleLastLat(),
			car.getCycleLastLon(),
			car.getAng(),
			car.getCycleLastSpeed(),
			car.getSum()
		);
	}
}
