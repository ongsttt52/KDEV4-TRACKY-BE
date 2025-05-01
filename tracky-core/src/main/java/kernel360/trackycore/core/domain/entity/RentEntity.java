package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import kernel360.trackycore.core.domain.entity.enums.RentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rent")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentEntity extends DateBaseEntity {

	@Id
	@Column(name = "rent_uuid", length = 8, nullable = false, updatable = false)
	private String rentUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mdn", nullable = false)
	private CarEntity car;

	@Column(name = "rent_stime", nullable = false)
	private LocalDateTime rentStime;

	@Column(name = "rent_etime", nullable = false)
	private LocalDateTime rentEtime;

	@Column(name = "renter_name", length = 20, nullable = false)
	private String renterName;

	@Column(name = "renter_phone", length = 13, nullable = false)
	private String renterPhone;

	@Column(name = "purpose", length = 20)
	private String purpose;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "rent_status", nullable = false)
	private RentStatus rentStatus;

	@Column(name = "rent_loc", length = 100)
	private String rentLoc;

	@Column(name = "rent_lat")
	private int rentLat;

	@Column(name = "rent_lon")
	private int rentLon;

	@Column(name = "return_loc", length = 100)
	private String returnLoc;

	@Column(name = "return_lat")
	private int returnLat;

	@Column(name = "return_lon")
	private int returnLon;

	// 생성자: 외부에서 직접 호출하지 못하도록 private 으로 변경
	private RentEntity(
		CarEntity car,
		String rentUuid,
		LocalDateTime rentStime,
		LocalDateTime rentEtime,
		String renterName,
		String renterPhone,
		String purpose,
		RentStatus rentStatus,
		String rentLoc,
		int rentLat,
		int rentLon,
		String returnLoc,
		int returnLat,
		int returnLon
	) {
		this.car = car;
		this.rentUuid = rentUuid;
		this.rentStime = rentStime;
		this.rentEtime = rentEtime;
		this.renterName = renterName;
		this.renterPhone = renterPhone;
		this.purpose = purpose;
		this.rentStatus = rentStatus;
		this.rentLoc = rentLoc;
		this.rentLat = rentLat;
		this.rentLon = rentLon;
		this.returnLoc = returnLoc;
		this.returnLat = returnLat;
		this.returnLon = returnLon;
	}

	// 정적 팩토리 메서드
	public static RentEntity create(CarEntity car,
		String rentUuid,
		LocalDateTime rentStime,
		LocalDateTime rentEtime,
		String renterName,
		String renterPhone,
		String purpose,
		RentStatus rentStatus,
		String rentLoc,
		int rentLat,
		int rentLon,
		String returnLoc,
		int returnLat,
		int returnLon
	) {
		return new RentEntity(
			car,
			rentUuid,
			rentStime,
			rentEtime,
			renterName,
			renterPhone,
			purpose,
			rentStatus,
			rentLoc,
			rentLat,
			rentLon,
			returnLoc,
			returnLat,
			returnLon
		);
	}

	public void update(CarEntity car,
		LocalDateTime rentStime,
		LocalDateTime rentEtime,
		String renterName,
		String renterPhone,
		String purpose,
		RentStatus rentStatus,
		String rentLoc,
		String returnLoc
	) {
		this.car = car;
		this.rentStime = rentStime;
		this.rentEtime = rentEtime;
		this.renterName = renterName;
		this.renterPhone = renterPhone;
		this.purpose = purpose;
		this.rentStatus = rentStatus;
		this.rentLoc = rentLoc;
		this.returnLoc = returnLoc;
	}

	public void updateStatus(RentStatus rentStatus) {
		this.rentStatus = rentStatus;
	}
}
