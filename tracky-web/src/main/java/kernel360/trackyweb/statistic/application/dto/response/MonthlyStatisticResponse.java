package kernel360.trackyweb.statistic.application.dto.response;

import java.util.List;

import com.querydsl.core.Tuple;

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

	public static MonthlyStatisticResponse from(MonthlyStatisticEntity e, List<Tuple> monthlyDataTuples) {

		List<MonthlyStats> monthlyStats = monthlyDataTuples.stream().map(tuple -> {
			int year = tuple.get(0, Integer.class);
			int month = tuple.get(1, Integer.class);
			int driveCount = tuple.get(2, Integer.class);
			double driveDistance = tuple.get(3, Double.class);

			return new MonthlyStats(year, month, driveCount, driveDistance);
		}).toList();

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
