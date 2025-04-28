package kernel360.trackyweb.statistic.application.dto.response;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public record MonthlyStatisticResponse(
	Integer totalCarCount,
	Integer nonOperatingCarCount,
	Double avgOperationRate,
	Long totalDriveCount,
	Integer totalDriveSec,
	Double totalDriveDistance
) {
	public static MonthlyStatisticResponse from(MonthlyStatisticEntity e) {
		return new MonthlyStatisticResponse(
			e.getTotalCarCount(),
			e.getNonOperatingCarCount(),
			e.getAvgOperationRate(),
			e.getTotalDriveSec(),
			e.getTotalDriveCount(),
			e.getTotalDriveDistance()
		);
	}
}
