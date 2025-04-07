package kernel360.trackycore.core.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.common.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "device")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class DeviceEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tid;

	private String mid;

	private String did;

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
