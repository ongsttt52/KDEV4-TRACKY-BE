package kernel360.trackycore.core.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	EXAMPLE_NOT_FOUND("EXAMPLE_404", "에러 샘플 코드입니다."),
	CAR_NOT_FOUND("CAR_001", "조회된 차량이 없습니다."),
	CAR_DUPLICATED("CAR_002", "중복된 차량 번호입니다."),
	DEVICE_NOT_FOUND("DEVICE_001", "조회된 장치가 없습니다."),
	BIZ_NOT_FOUND("BIZ_001", "조회된 업체가 없습니다."),
	RENT_NOT_FOUND("RENT_001", "조회된 렌트가 없습니다."),

	LOCATION_NOT_FOUND("LOCATION_001", "조회된 위치가 없습니다."),
	DRIVE_NOT_FOUND("DRIVE_001", "조회된 주행 정보가 없습니다."),
	GPS_NOT_FOUND("GPS_001", "조회된 GPS 정보가 없습니다."),

	MEMBER_NOT_FOUND("MEMBER_001", "존재하지 않은 회원입니다."),
	MEMBER_WRONG_PWD("MEMBER_486", "비밀번호가 일치하지 않습니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
