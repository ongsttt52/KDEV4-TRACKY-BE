package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.uuid.Generators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
public class GpsHistoryEntity {

	@Id
	@Column(name = "drive_seq", nullable = false)
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID driveSeq;    //주행기록 시퀀스

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drive_id", nullable = false)
	private DriveEntity drive;    //주행ID 외래키

	@Column(name = "o_time")
	private LocalDateTime oTime;    //발생시간

	private int sec; // 발생시간 - 초

	private String gcd;        //GPS상태

	private int lat;    //GPS위도

	private int lon;    //GPS경도

	private int ang;    //방향

	private int spd;    //속도

	private double sum;    //단건 주행거리

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;    //생성시간

	@PrePersist
	private void ensureId() {
		if (this.driveSeq == null) {
			this.driveSeq = Generators.timeBasedEpochGenerator().generate();
		}
	}

	public GpsHistoryEntity(DriveEntity drive, LocalDateTime oTime, int sec, String gcd, int lat, int lon, int ang,
		int spd,
		double sum) {
		this.drive = drive;
		this.oTime = oTime;
		this.sec = sec;
		this.gcd = gcd;
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}
}
