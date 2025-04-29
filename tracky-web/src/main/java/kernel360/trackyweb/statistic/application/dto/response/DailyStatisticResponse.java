package kernel360.trackyweb.statistic.application.dto.response;

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
		double totalDrivingDistanceKm  // 저장 단위는 km로 가정
	) {
	}

	public record HourlyStat(
		int hour,
		int driveCount
	) {
	}

	public static DailyStatisticResponse from(DailyStatisticEntity e, List<Integer> hourlyDriveCounts) {
		int count = 0;
		List<HourlyStat> hourlyStatList = new ArrayList<>();
		for (Integer driveCount : hourlyDriveCounts) {
			hourlyStatList.add(new HourlyStat(count++, driveCount));
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
			hourlyStatList
		);
	}
}