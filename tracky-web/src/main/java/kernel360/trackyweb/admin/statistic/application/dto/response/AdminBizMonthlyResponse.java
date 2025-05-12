package kernel360.trackyweb.admin.statistic.application.dto.response;

public record AdminBizMonthlyResponse(
	int year,
	int month,
	int driveCount
) {
}

