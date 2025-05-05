package kernel360.trackyweb.car.application.dto.internal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CarCountWithBizId(
	Long bizId,
	int carCount
) {
	public static Map<Long, Integer> toMap(List<CarCountWithBizId> dtoList) {
		return dtoList.stream()
			.collect(Collectors.toMap(
				CarCountWithBizId::bizId,
				CarCountWithBizId::carCount
			));
	}
}
