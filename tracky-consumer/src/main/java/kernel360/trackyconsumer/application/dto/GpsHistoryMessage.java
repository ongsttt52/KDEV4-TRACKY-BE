package kernel360.trackyconsumer.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GpsHistoryMessage {

	private String mdn;

	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime oTime;  // 발생시간

	private int cCnt;

	private List<CycleGpsRequest> cList;
}
