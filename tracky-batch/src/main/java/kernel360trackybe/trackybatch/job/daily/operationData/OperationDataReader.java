package kernel360trackybe.trackybatch.job.daily.operationData;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import kernel360trackybe.trackybatch.job.daily.carData.dto.DailyCarData;

@Component
public class OperationDataReader implements ItemReader<DailyCarData> {

	@Override
	public DailyCarData read() throws
		Exception,
		UnexpectedInputException,
		ParseException,
		NonTransientResourceException {
		return null;
	}

}
