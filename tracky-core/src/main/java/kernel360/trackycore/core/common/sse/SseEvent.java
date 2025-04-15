package kernel360.trackycore.core.common.vo;

import lombok.Getter;

@Getter
public enum SseEvent {
	CAR_CREATED("car_event", "create", "차량을 등록 하였습니다."),
	CAR_UPDATED("car_event", "update", "차량을 수정 하였습니다."),
	CAR_DELETED("car_event", "delete", "차량을 삭제 하였습니다.");

	private final String code;
	private final String method;
	private final String message;

	SseEvent(String code, String method, String message) {
		this.code = code;
		this.method = method;
		this.message = message;
	}

}
