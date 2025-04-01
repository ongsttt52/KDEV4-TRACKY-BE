package kernel360.trackycore.core.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "gpshistory")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@IdClass(GpsHistoryId.class)
public class GpsHistoryEntity {

	@Id
	@Column(name = "drive_seq", nullable = false)
	private Long driveSeq;

	@Id
	@JoinColumn(name = "drive_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private DriveEntity drive;

	@Column(name = "o_time")
	private LocalDateTime oTime;

	private String gcd;

	private long lat;

	private long lon;

	private int ang;

	private int spd;

	private int sum;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	public GpsHistoryEntity(long maxSeq, DriveEntity drive, LocalDateTime oTime, String gcd, long lat, long lon, int ang, int spd,
		int sum) {
		this.driveSeq = maxSeq;
		this.drive = drive;
		this.oTime = oTime;
		this.gcd = gcd;
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}
}
