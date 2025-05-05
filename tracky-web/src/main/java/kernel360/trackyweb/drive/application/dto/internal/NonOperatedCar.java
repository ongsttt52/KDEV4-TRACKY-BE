package kernel360.trackyweb.drive.application.dto.internal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record NonOperatedCar(
        Long bizId,
        int nonOperatedCar
) {
    public static Map<Long, Integer> toMap(List<NonOperatedCar> dtoList) {
        return dtoList.stream()
                .collect(Collectors.toMap(
                        NonOperatedCar::bizId,
                        NonOperatedCar::nonOperatedCar
                ));
    }
}
