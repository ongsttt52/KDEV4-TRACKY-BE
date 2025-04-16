package kernel360.trackyemulator.application.service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel360.trackycore.core.common.entity.vo.EmulatorInfo;
import kernel360.trackycore.core.common.entity.vo.GpsInfo;
import kernel360.trackyemulator.domain.EmulatorInstance;

public record CarOnOffRequest(

	String mdn,

	EmulatorInfo emulatorInfo,

	GpsInfo gpsInfo,

	String gcd,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime onTime,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime offTime

) {

	/**
	 * 시동 ON DTO 생성
	 */
	public static CarOnOffRequest ofOn(EmulatorInstance car) {
		return new CarOnOffRequest(
			car.getMdn(),
			car.getEmulatorInfo(),
			car.getCycleLastGpsInfo(),
			"A",
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
			car.getEmulatorInfo(),
			car.offGpsInfo(),
			"A",
			car.getCarOnTime(),
			car.getCarOffTime()
		);
	}
}
