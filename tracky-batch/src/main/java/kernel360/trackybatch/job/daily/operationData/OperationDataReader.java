package kernel360.trackybatch.job.daily.operationData;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import kernel360.trackybatch.job.daily.carData.dto.DailyCarData;

@Component
public class OperationDataReader implements ItemReader<DailyCarData> {

	@Override
	public DailyCarData read() {
		return null;
	}

}
