package kernel360.trackyweb.statistic.application.dto.response;

import java.util.List;
import java.util.stream.IntStream;

import kernel360.trackycore.core.domain.entity.MonthlyStatisticEntity;

public record MonthlyStatisticResponse(
	Summary summary,
	List<MonthlyStat> monthlyStat
) {
	public record Summary(
		int totalCarCount,
		int nonOperatingCarCount,
		double averageOperationRate,
		long totalDrivingSeconds,
		int totalDriveCount,
		double totalDrivingDistanceKm  // 저장 단위는 km로 가정
	) {
	}

	public record MonthlyStat(
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
		List<MonthlyStat> monthlyStats = IntStream.range(0, currentMonth)
			.mapToObj(i -> new MonthlyStat(
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
