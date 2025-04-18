package kernel360.trackyemulator.infrastructure.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	MDN_NOT_FOUND("EM001", "차량관리번호(MDN) 목록이 없습니다."),
	MDN_INSUFFICIENT("EM002", "사용 가능한 차량관리번호(MDN)가 부족합니다."),
	CYCLE_SEND_FAILED("EM003", "주기 데이터 전송에 실패했습니다."),
	MDN_LIST_REQUEST_FAILED("EM004", "차량관리번호(MDN) 리스트를 요청하는 중 오류가 발생했습니다."),
	TOKEN_REQUEST_FAILED("EM005", "토큰 요청에 실패했습니다."),
	START_REQUEST_FAILED("EM006", "시동 ON 전송에 실패했습니다."),
	STOP_REQUEST_FAILED("EM007", "시동 OFF 전송에 실패했습니다."),
	INTERNAL_SERVER_ERROR("EM000", "서버에서 오류가 발생했습니다.");

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
