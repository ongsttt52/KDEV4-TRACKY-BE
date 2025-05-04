package kernel360.trackyweb.statistic.application.dto.response;

import java.util.List;
import java.util.stream.IntStream;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public record MonthlyStatisticResponse(
	Summary summary,
	List<MonthlyStats> monthlyStat
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
		long driveDistance
	) {
	}

	public static MonthlyStatisticResponse from(MonthlyStatisticEntity e,
		List<Integer> monthlyDriveCounts,
		List<Long> monthlyDriveDistances
	) {
		int currentMonth = e.getDate().getMonthValue();
		List<MonthlyStats> monthlyStats = IntStream.range(0, currentMonth)
			.mapToObj(i -> new MonthlyStats(
				e.getDate().getYear(),
				i + 1,
				monthlyDriveCounts.get(i),
				monthlyDriveDistances.get(i)
			)).toList();

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
