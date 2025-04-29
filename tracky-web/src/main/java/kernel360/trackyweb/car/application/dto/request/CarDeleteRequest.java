package kernel360.trackyweb.car.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CarDeleteRequest (
	@NotBlank(message = "MDN이 없습니다.")
	String mdn
) { }
