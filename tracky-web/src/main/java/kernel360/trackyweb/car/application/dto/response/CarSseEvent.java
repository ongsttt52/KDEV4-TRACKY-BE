package kernel360.trackyweb.car.application.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarSseEvent {
	private String event;
	private String method;
	private String message;
	private LocalDateTime createdAt;

	private CarSseEvent(String event, String method, String message) {
		this.event = event;
		this.method = method;
		this.message = message;
		this.createdAt = LocalDateTime.now();
	}

	public static CarSseEvent create(String event, String method, String message) {
		return new CarSseEvent(event, method, message);
	}
}
