package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "daily_statistic")
public class DailyStatisticEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "biz_uuid", nullable = false, length = 8)
	private String bizUuid;

	@Column(name = "date")
	private LocalDateTime date;

	@Column(name = "total_car_count")
	private Integer totalCarCount;

	@Column(name = "daily_drive_car_count")
	private Integer dailyDriveCarCount;

	@Column(name = "avg_operation_rate")
	private Double avgOperationRate;

	@Column(name = "daily_drive_sec")
	private Long dailyDriveSec;

	@Column(name = "daily_drive_count")
	private Integer dailyDriveCount;

	@Column(name = "daily_drive_distance")
	private Double dailyDriveDistance;

	// 외래 키 관계 (Biz 엔티티와 연결)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biz_uuid", referencedColumnName = "biz_uuid", insertable = false, updatable = false)
	private BizEntity biz;

}
