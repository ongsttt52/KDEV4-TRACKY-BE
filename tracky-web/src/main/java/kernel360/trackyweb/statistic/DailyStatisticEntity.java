package kernel360.trackyweb.statistic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_statistic")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyStatisticEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "biz_uuid", nullable = false)
	private String bizUuid;

	@Column(name = "total_car_count", nullable = false)
	private int totalCarCount;

	@Column(name = "daily_drive_car_count", nullable = false)
	private int dailyDriveCarCount;

	@Column(name = "avg_operation_rate", nullable = false)
	private int avgOperationRate;

	@Column(name = "daily_drive_time", nullable = false)
	private long dailyDriveSec;

	@Column(name = "daily_drive_count", nullable = false)
	private int dailyDriveCount;

	@Column(name = "daily_drive_distance", nullable = false)
	private double dailyDriveDistance;
}
