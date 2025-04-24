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
@Table(name = "device")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "tid", length = 10, nullable = false)
	private String tid;

	@Column(name = "mid", length = 10, nullable = false)
	private String mid;

	@Column(name = "did", length = 10, nullable = false)
	private String did;

	@Column(name = "pv", length = 10, nullable = false)
	private String pv;

	private DeviceEntity(String tid, String mid, String did, String pv) {
		this.tid = tid;
		this.mid = mid;
		this.did = did;
		this.pv = pv;
	}

	public static DeviceEntity create(String tid, String mid, String did, String pv) {
		return new DeviceEntity(tid, mid, did, pv);
	}
}