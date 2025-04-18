package kernel360.trackyweb.rent.application.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentSseEvent {
	private String event;
	private String method;
	private String message;
	private LocalDateTime createdAt;

	private RentSseEvent(String event, String method, String message) {
		this.event = event;
		this.method = method;
		this.message = message;
		this.createdAt = LocalDateTime.now();
	}

	public static RentSseEvent create(String event, String method, String message) {
		return new RentSseEvent(event, method, message);
	}
}
