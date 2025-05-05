package kernel360.trackyweb.statistic.domain.dto;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public record Summary(
	int totalCarCount,
	int nonOperatingCarCount,
	double averageOperationRate,
	long totalDrivingSeconds,
	int totalDriveCount,
	double totalDrivingDistance

) {
	public static Summary from(MonthlyStatisticEntity e) {
		return new Summary(
			e.getTotalCarCount(),
			e.getNonOperatingCarCount(),
			e.getAvgOperationRate(),
			e.getTotalDriveSec(),
			e.getTotalDriveCount(),
			e.getTotalDriveDistance()
		);
	}
}
