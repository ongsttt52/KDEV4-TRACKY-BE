package kernel360.trackycore.core.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private Long id;

	@Column(name = "biz_id", nullable = false)
	private Long bizId;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "total_car_count", nullable = false)
	private Integer totalCarCount;

	@Column(name = "daily_drive_car_count", nullable = false)
	private Integer dailyDriveCarCount;

	@Column(name = "avg_operation_rate", nullable = false)
	private Double avgOperationRate;

	@Column(name = "daily_drive_sec", nullable = false)
	private Long dailyDriveSec;

	@Column(name = "daily_drive_count", nullable = false)
	private Integer dailyDriveCount;

	@Column(name = "daily_drive_distance", nullable = false)
	private Double dailyDriveDistance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biz_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BizEntity biz;

	private DailyStatisticEntity(
		Long bizId,
		LocalDate date,
		Integer totalCarCount,
		Integer dailyDriveCarCount,
		Double avgOperationRate,
		Long dailyDriveSec,
		Integer dailyDriveCount,
		Double dailyDriveDistance
	) {
		this.bizId = bizId;
		this.date = date;
		this.totalCarCount = totalCarCount;
		this.dailyDriveCarCount = dailyDriveCarCount;
		this.avgOperationRate = avgOperationRate;
		this.dailyDriveSec = dailyDriveSec;
		this.dailyDriveCount = dailyDriveCount;
		this.dailyDriveDistance = dailyDriveDistance;
	}

	public static DailyStatisticEntity create(
		Long bizId,
		LocalDate date,
		Integer totalCarCount,
		Integer dailyDriveCarCount,
		Double avgOperationRate,
		Long dailyDriveSec,
		Integer dailyDriveCount,
		Double dailyDriveDistance
	) {
		return new DailyStatisticEntity(bizId, date, totalCarCount, dailyDriveCarCount, avgOperationRate,
			dailyDriveSec, dailyDriveCount, dailyDriveDistance);
	}
}
