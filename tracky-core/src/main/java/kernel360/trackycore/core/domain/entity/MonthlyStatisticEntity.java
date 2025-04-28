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
@Table(name = "monthly_statistic")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyStatisticEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "biz_uuid", nullable = false, length = 8)
	private String bizUuid;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "total_car_count")
	private Integer totalCarCount;

	@Column(name = "non_operating_car_count")
	private Integer nonOperatingCarCount;

	@Column(name = "avg_operation_rate")
	private Double avgOperationRate;

	@Column(name = "total_drive_sec")
	private Long totalDriveSec;

	@Column(name = "total_drive_count")
	private Integer totalDriveCount;

	@Column(name = "total_drive_distance")
	private Double totalDriveDistance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biz_uuid", referencedColumnName = "biz_uuid", insertable = false, updatable = false)
	private BizEntity biz;

	private MonthlyStatisticEntity(
		String bizUuid,
		LocalDate date,
		Integer totalCarCount,
		Integer nonOperatingCarCount,
		Double avgOperationRate,
		Long totalDriveSec,
		Integer totalDriveCount,
		Double totalDriveDistance
	) {
		this.bizUuid = bizUuid;
		this.date = date;
		this.totalCarCount = totalCarCount;
		this.nonOperatingCarCount = nonOperatingCarCount;
		this.avgOperationRate = avgOperationRate;
		this.totalDriveSec = totalDriveSec;
		this.totalDriveCount = totalDriveCount;
		this.totalDriveDistance = totalDriveDistance;
	}
	
	public static MonthlyStatisticEntity create(
		String bizUuid,
		LocalDate date,
		Integer totalCarCount,
		Integer nonOperatingCarCount,
		Double avgOperationRate,
		Long totalDriveSec,
		Integer totalDriveCount,
		Double totalDriveDistance
	) {
		return new MonthlyStatisticEntity(
			bizUuid,
			date,
			totalCarCount,
			nonOperatingCarCount,
			avgOperationRate,
			totalDriveSec,
			totalDriveCount,
			totalDriveDistance
		);
	}
}
