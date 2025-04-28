package kernel360.trackyweb.statistic.application.dto.response;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;

public record DailyStatisticResponse(
	Integer totalCarCount,
	Integer dailyDriveCarCount,
	Double avgOperationRate,
	Long dailyDriveSec,
	Integer dailyDriveCount,
	Double dailyDriveDistance
) {
	public static DailyStatisticResponse from(DailyStatisticEntity e) {
		return new DailyStatisticResponse(
			e.getTotalCarCount(),
			e.getDailyDriveCarCount(),
			e.getAvgOperationRate(),
			e.getDailyDriveSec(),
			e.getDailyDriveCount(),
			e.getDailyDriveDistance()
		);
	}
}
