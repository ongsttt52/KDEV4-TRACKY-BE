package kernel360.trackyweb.statistic.application.dto.internal;

public record DashboardStatistic(
	double avgOperationRate,
	int nonOperatingCarCount,
	int totalDriveCount
) {
}
