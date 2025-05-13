package kernel360.trackyweb.admin.statistic.application.dto.response;

import java.util.List;

import kernel360.trackycore.core.domain.entity.enums.CarType;

public record AdminGraphStatsResponse(
	List<CarCount> carCountWithBizName,
	List<CarTypeCount> carTypeCounts,
	List<OperationRate> operationRateWithBizName,
	List<NonOperatedCar> nonOperatedCarWithBizName
) {
	public record CarCount(String bizName, int carCount) {
	}

	public record CarTypeCount(CarType carType, long carTypeCount) {
	}

	public record OperationRate(String bizName, double rate) {
	}

	public record NonOperatedCar(String bizName, int nonOperatedCarCount) {
	}

	public static AdminGraphStatsResponse toResponse(
		List<CarCount> carCount, List<CarTypeCount> carTypeCounts,
		List<OperationRate> operationRateWithBizName, List<NonOperatedCar> nonOperatedCarWithBizName) {
		return new AdminGraphStatsResponse(carCount, carTypeCounts, operationRateWithBizName,
			nonOperatedCarWithBizName);
	}
}
