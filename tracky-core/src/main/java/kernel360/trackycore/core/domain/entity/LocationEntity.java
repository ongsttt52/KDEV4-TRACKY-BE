package kernel360.trackycore.core.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "location")
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocationEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "drive_start_lon")
	private int driveStartLon;

	@Column(name = "drive_start_lat")
	private int driveStartLat;

	@Column(name = "drive_end_lon")
	private int driveEndLon;

	@Column(name = "drive_end_lat")
	private int driveEndLat;

	public void updateEndLocation(int lat, int lon) {
		this.driveEndLat = lat;
		this.driveEndLon = lon;
	}

	public static LocationEntity create(int driveStartLon, int driveStartLat) {
		LocationEntity location = new LocationEntity();
		location.driveStartLon = driveStartLon;
		location.driveStartLat = driveStartLat;

		return location;
	}
}
