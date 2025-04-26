package kernel360.trackyconsumer.consumer.application.dto;

import java.util.List;
import kernel360.trackycore.core.domain.entity.GpsHistoryEntity;

public record GpsProcessResult (
    List<GpsHistoryEntity> gpsHistoryList,
    double totalDistance
) {

    public static GpsProcessResult of(List<GpsHistoryEntity> gpsHistoryList, double totalDistance) {
        return new GpsProcessResult(gpsHistoryList, totalDistance);
    }
}