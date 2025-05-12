package kernel360.trackyconsumer.consumer.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record GpsHistoryMessage(
	String mdn,

	@JsonFormat(pattern = "yyyyMMddHHmm")
	LocalDateTime oTime,

	int cCnt,

	List<CycleGpsRequest> cList
) {
}
