package kernel360.trackyweb.admin.statistic.application.dto;

public record AdminBizStatisticResponse(
	int dailyDriveCount,
	double dailyDriveDistance,
	int dailyDriveSec
) {
}
