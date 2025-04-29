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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drive")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DriveEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mdn", nullable = false)
	private CarEntity car;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rent_uuid", nullable = false)
	private RentEntity rent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drive_loc_id", nullable = false)
	private LocationEntity location;

	@Column(name = "drive_distance")
	private double driveDistance;

	@Column(name = "drive_on_time")
	private LocalDateTime driveOnTime;

	@Column(name = "drive_off_time")
	private LocalDateTime driveOffTime;

	@Column(name = "memo", columnDefinition = "TEXT")
	private String memo;

	@Column(name = "skip_count")
	private int skipCount;

	public void off(double distance, LocalDateTime offTime) {
		this.driveDistance = distance;
		this.driveOffTime = offTime;
	}

	public static DriveEntity create(CarEntity car, RentEntity rent, LocationEntity location,
		LocalDateTime onTime) {
		DriveEntity drive = new DriveEntity();
		drive.car = car;
		drive.rent = rent;
		drive.location = location;
		drive.driveOnTime = onTime;
		drive.driveDistance = 0;

		return drive;
	}

	public void skipCount(int cnt) {
		this.skipCount += cnt;
	}
}
