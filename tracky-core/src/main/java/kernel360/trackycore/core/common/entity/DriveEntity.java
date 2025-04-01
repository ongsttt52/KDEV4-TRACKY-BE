package kernel360.trackycore.core.common.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kernel360.trackycore.core.common.base.DateBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "drive")
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DriveEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mdn;

	@Column(name = "rent_uuid")
	private String rentUuid;

	@Column(name = "device_id")
	private long deviceId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drive_loc_id")
	private LocationEntity location;

	@Column(name = "drive_distance")
	private int driveDistance;

	@Column(name = "drive_on_time")
	private LocalDateTime driveOnTime;

	@Column(name = "drive_off_time")
	private LocalDateTime driveOffTime;
  
	public void updateDistance(int sum) {
		this.driveDistance = sum;
	}

	public void updateOffTime(LocalDateTime offTime) {
		this.driveOffTime = offTime;
	}

	public static DriveEntity create(String mdn, String rentUuid, long deviceId, LocationEntity location, LocalDateTime onTime) {
		DriveEntity drive = new DriveEntity();
		drive.mdn = mdn;
		drive.rentUuid = rentUuid;
		drive.deviceId = deviceId;
		drive.location = location;
		drive.driveOnTime = onTime;
		drive.driveDistance = 0;

		return drive;
  }
}
