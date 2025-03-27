package kernel360.trackycore.core.infrastructure.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.infrastructure.base.DateBaseEntity;
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

	@Column(name = "rent_id")
	private String rentId;


	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "drive_loc_id")
	private String driveLocId;

	@Column(name = "drive_distance")
	private String driveDistance;

	@Column(name = "drive_on_time")
	private String driveOnTime;

	@Column(name = "drive_off_time")
	private String driveOffTime;
}
