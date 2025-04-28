package kernel360.trackybatch.job.daily.carData.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyCarData {
	private String bizUuid;
	private LocalDateTime date;
	private int totalCarCount;
	private int operatingCarCount;
}
