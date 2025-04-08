package kernel360.trackyweb.dashboard.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Statistics {

	private final double totalDriveDistance;
	private final long totalRentCount;
	private final long totalCarCount;
	private final long totalRentDurationInMinutes;
	private final long totalDriveDurationInMinutes;

	public Statistics(
		double totalDriveDistance,
		long totalRentCount,
		long totalCarCount,
		long totalRentDurationInMinutes,
		long totalDriveDurationInMinutes
	) {
		this.totalDriveDistance = totalDriveDistance;
		this.totalRentCount = totalRentCount;
		this.totalCarCount = totalCarCount;
		this.totalRentDurationInMinutes = totalRentDurationInMinutes;
		this.totalDriveDurationInMinutes = totalDriveDurationInMinutes;
	}

	public static Statistics create(
		double totalDriveDistance,
		long totalRentCount,
		long totalCarCount,
		long totalRentDurationInMinutes,
		long totalDriveDurationInMinutes
	) {
		return new Statistics(
			totalDriveDistance,
			totalRentCount,
			totalCarCount,
			totalRentDurationInMinutes,
			totalDriveDurationInMinutes
		);
	}

}
