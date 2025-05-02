package kernel360.trackyweb.drive.application.dto.internal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record OperationCarCount(
	String bizId, int carCount) {

	public static Map<String, Integer> toMap(List<OperationCarCount> dtoList) {
		return dtoList.stream()
			.collect(Collectors.toMap(
				OperationCarCount::bizId,
				OperationCarCount::carCount
			));
	}
}

