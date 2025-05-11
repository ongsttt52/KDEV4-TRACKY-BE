package kernel360.trackyweb.admin.statistic.application.dto.response;

public record AdminBizStatisticResponse(
	int dailyDriveCount,
	double dailyDriveDistance,
	int dailyDriveSec
) {
}
