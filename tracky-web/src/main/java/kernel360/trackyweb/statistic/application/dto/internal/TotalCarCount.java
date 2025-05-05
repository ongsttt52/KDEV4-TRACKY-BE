package kernel360.trackyweb.statistic.application.dto.internal;

import kernel360.trackyweb.car.application.dto.internal.CarCountWithBizId;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TotalCarCount(
        Long bizId,
        int totalCarCount
) {
    public static Map<Long, Integer> toMap(List<TotalCarCount> dtoList) {
        return dtoList.stream()
                .collect(Collectors.toMap(
                        TotalCarCount::bizId,
                        TotalCarCount::totalCarCount
                ));
    }
}
