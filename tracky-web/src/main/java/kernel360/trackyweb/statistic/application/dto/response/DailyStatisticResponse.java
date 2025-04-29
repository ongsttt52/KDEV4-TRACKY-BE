package kernel360.trackyweb.statistic.application.dto.response;

import java.util.List;
import java.util.stream.IntStream;

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
		double totalDrivingDistanceKm  // 저장 단위는 km로 가정
	) {
	}

	public record HourlyStat(
		int hour,
		int driveCount
	) {
	}

	public static DailyStatisticResponse from(DailyStatisticEntity e, List<Integer> hourlyDriveCounts) {
		List<DailyStatisticResponse.HourlyStat> hourlyStats = IntStream.range(0, 24)
			.mapToObj(i -> new DailyStatisticResponse.HourlyStat(
				i,
				hourlyDriveCounts.get(i)
			)).toList();

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