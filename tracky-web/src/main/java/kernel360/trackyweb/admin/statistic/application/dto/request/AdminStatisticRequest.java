package kernel360.trackyweb.admin.statistic.application.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public record AdminStatisticRequest(
	String bizName,
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate
) {
}