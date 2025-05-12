package kernel360.trackyweb.dashboard.application.dto.response;

import java.util.List;

public record DashboardStatisticsResponse(
	//당월 평균 운행률
	double avgOperationRate,
	//당월 미운행 차량 수
	int nonOperatingCarCount,
	//당월 총 운행 수
	int totalDriveCount,
	//일별 운행 수
	List<Integer> dailyDriveCount
) {
	public DashboardStatisticsResponse(
		double avgOperationRate,
		int nonOperatingCarCount,
		int totalDriveCount,
		List<Integer> dailyDriveCount
	) {
		this.avgOperationRate = avgOperationRate;
		this.nonOperatingCarCount = nonOperatingCarCount;
		this.totalDriveCount = totalDriveCount;
		this.dailyDriveCount = dailyDriveCount;
	}

	public static DashboardStatisticsResponse create(
		double avgOperationRate,
		int nonOperationCarCount,
		int totalDriveCount,
		List<Integer> dailyDriveCount
	) {
		return new DashboardStatisticsResponse(
			avgOperationRate,
			nonOperationCarCount,
			totalDriveCount,
			dailyDriveCount
		);
	}

}
