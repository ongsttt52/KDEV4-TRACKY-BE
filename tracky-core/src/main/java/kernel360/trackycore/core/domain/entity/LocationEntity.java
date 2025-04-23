package kernel360.trackycore.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "drive_start_lon", length = 100, nullable = false, columnDefinition = "VARCHAR(100)")
	private int driveStartLon;

	@Column(name = "drive_start_lat", length = 100, nullable = false, columnDefinition = "VARCHAR(100)")
	private int driveStartLat;

	@Column(name = "drive_end_lon", length = 100, nullable = true, columnDefinition = "VARCHAR(100)")
	private int driveEndLon;

	@Column(name = "drive_end_lat", length = 100, nullable = true, columnDefinition = "VARCHAR(100)")
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
