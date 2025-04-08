package kernel360.trackyweb.car.application.mapper;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarEvent {
	private String event;
	private String method;
	private String message;
	private LocalDateTime createdAt;

	private CarEvent(String event, String method, String message) {
		this.event = event;
		this.method = method;
		this.message = message;
		this.createdAt = LocalDateTime.now();
	}

	public static CarEvent create(String event, String method, String message) {
		return new CarEvent(event, method, message);
	}
}
