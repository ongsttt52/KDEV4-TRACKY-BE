package kernel360.trackycore.core.domain.entity;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "gpshistory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GpsHistoryEntity implements Persistable<UUID> {

	@Id
	@Column(name = "drive_seq", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
	private UUID driveSeq;    //주행기록 시퀀스

	@Column(name = "mdn", nullable = false)
	private String mdn;

	@Column(name = "o_time", nullable = false)
	private LocalDateTime oTime;    //발생시간

	@Column(name = "gcd", length = 10, nullable = false)
	private String gcd;        //GPS상태

	@Column(name = "lat", nullable = false)
	private int lat;    //GPS위도

	@Column(name = "lon", nullable = false)
	private int lon;    //GPS경도

	@Column(name = "ang", nullable = false)
	private int ang;    //방향

	@Column(name = "spd", nullable = false)
	private int spd;    //속도

	@Column(name = "sum", nullable = false)
	private double sum;    //단건 주행거리

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;    //생성시간

	// JPA에서 엔티티가 로드될 때 'new' 상태가 아니도록 플래그를 설정
	@Transient // 데이터베이스에 매핑되지 않음을 명시
	private boolean isNew = true;

	public GpsHistoryEntity(String mdn, LocalDateTime oTime, String gcd, int lat, int lon, int ang,
		int spd,
		double sum) {
		this.driveSeq = Generators.timeBasedEpochGenerator().generate();
		this.mdn = mdn;
		this.oTime = oTime;
		this.gcd = gcd;
		this.lat = lat;
		this.lon = lon;
		this.ang = ang;
		this.spd = spd;
		this.sum = sum;
	}

	@Override
	public UUID getId() {
		return this.driveSeq;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}

	// 저장 후 isNew 상태를 false로 변경하는 헬퍼 메서드 (필요하다면)
	@PostPersist // JPA LifeCycle Callback: 엔티티가 DB에 저장된 후 호출
	@PostLoad // 엔티티가 DB에서 로드된 후 호출
	public void setNotNew() {
		this.isNew = false;
	}
}
