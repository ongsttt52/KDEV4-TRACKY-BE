package kernel360.trackyweb.statistic.application.dto.response;

import java.util.List;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public record MonthlyStatisticResponse(
	Summary summary,
	List<MonthlyStats> monthlyStats
) {
	public record Summary(
		int totalCarCount,
		int nonOperatingCarCount,
		double averageOperationRate,
		long totalDrivingSeconds,
		int totalDriveCount,
		double totalDrivingDistance
	) {
	}

	public record MonthlyStats(
		int year,
		int month,
		int driveCount,
		double driveDistance
	) {
	}

	public static MonthlyStatisticResponse from(MonthlyStatisticEntity e, List<MonthlyStats> monthlyStats) {

		return new MonthlyStatisticResponse(
			new Summary(
				e.getTotalCarCount(),
				e.getNonOperatingCarCount(),
				e.getAvgOperationRate(),
				e.getTotalDriveSec(),
				e.getTotalDriveCount(),
				e.getTotalDriveDistance()
			),
			monthlyStats
		);
	}
}
