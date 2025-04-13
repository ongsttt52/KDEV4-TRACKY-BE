package kernel360.trackyweb.rent.presentation.mapper;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentEvent {
	private String event;
	private String method;
	private String message;
	private LocalDateTime createdAt;

	private RentEvent(String event, String method, String message) {
		this.event = event;
		this.method = method;
		this.message = message;
		this.createdAt = LocalDateTime.now();
	}

	public static RentEvent create(String event, String method, String message) {
		return new RentEvent(event, method, message);
	}
}
