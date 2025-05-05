package kernel360.trackyweb.statistic.presentation.dto;

import java.util.ArrayList;
import java.util.List;

import kernel360.trackycore.core.domain.entity.DailyStatisticEntity;

public record DailyStatisticResponse(
	Summary summary,
	List<HourlyStat> hourlyStats
) {
	public record Summary(
		int totalCarCount,
		int dailyDriveCarCount,
		double averageOperationRate,
		long totalDrivingSeconds,
		int totalDriveCount,
		double totalDrivingDistance
	) {
	}

	public record HourlyStat(
		int hour,
		long driveCount
	) {
	}

	public static DailyStatisticResponse from(DailyStatisticEntity e, long[] hourlyDriveCounts) {
		List<HourlyStat> hourlyStats = new ArrayList<>();

		for (int i = 0; i < hourlyDriveCounts.length; i++) {
			HourlyStat hourlyStat = new HourlyStat(i, hourlyDriveCounts[i]);
			hourlyStats.add(hourlyStat);
		}

		return new DailyStatisticResponse(
			new Summary(
				e.getTotalCarCount(),
				e.getDailyDriveCarCount(),
				e.getAvgOperationRate(),
				e.getDailyDriveSec(),
				e.getDailyDriveCount(),
				e.getDailyDriveDistance()
			),
			hourlyStats
		);
	}
}