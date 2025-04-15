package kernel360.trackycore.core.infrastructure.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	EXAMPL_NOT_FOUND("EXAMPL_404", "에러 샘플 코드입니다."),
	CAR_NOT_FOUND("CAR_001", "조회된 차량이 없습니다."),
	CAR_DUPLICATED("CAR_002", "중복된 차량 번호입니다."),
	DEVICE_NOT_FOUND("DEVICE_001", "조회된 장치가 없습니다."),
	BIZ_NOT_FOUND("BIZ_001", "조회된 업체가 없습니다."),
	RENT_NOT_FOUND("RENT_001", "조회된 대여가 없습니다."),

	MEMBER_NOT_FOUND("MEMBER_001", "존재하지 않은 회원입니다."),
	MEMBER_WRONG_PWD("MEMBER_486", "비밀번호가 일치하지 않습니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
