package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackyemulator.domain.EmulatorInstance;

public record CarOnOffRequest(

	EmulatorInfo emulatorInfo,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime onTime,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime offTime,

	int lat,
	int lon,
	int ang,
	int spd,
	double sum

) {

	/**
	 * 시동 ON DTO 생성
	 */
	public static CarOnOffRequest ofOn(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getEmulatorInfo(),
			car.getCarOnTime(),
			null,
			car.getCycleLastLat(),
			car.getCycleLastLon(),
			0,
			0,
			0.0
		);
	}

	/**
	 * 시동 OFF DTO 생성
	 */
	public static CarOnOffRequest ofOff(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getEmulatorInfo(),
			car.getCarOnTime(),
			car.getCarOffTime(),
			car.getCycleLastLat(),
			car.getCycleLastLon(),
			car.getAng(),
			car.getCycleLastSpeed(),
			car.getSum()
		);
	}
}
