package kernel360.trackycore.core.common.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import kernel360.trackycore.core.common.base.DateBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "rent")
@Getter
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RentEntity extends DateBaseEntity {

	@Id
	@Column(name = "rent_uuid")
	private String rentUuid;    // 대여 고유 UUID

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mdn")
	private CarEntity car;

	@Column(name = "rent_stime")
	private LocalDateTime rentStime;    // 대여 시작 시간

	@Column(name = "rent_etime")
	private LocalDateTime rentEtime;    // 대여 종료 시간

	@Column(name = "renter_name")
	private String renterName;            // 사용자 이름

	@Column(name = "renter_phone")
	private String renterPhone;        // 사용자 전화번호

	private String purpose;                // 차량 사용 목적

	@Column(name = "rent_status")
	private String rentStatus;            // 대여 상태

	@Column(name = "rent_loc")
	private String rentLoc;            // 대여 위치

	@Column(name = "rent_lat")
	private int rentLat;            // 대여 경도

	@Column(name = "rent_lon")
	private int rentLon;            // 대여 위도

	@Column(name = "return_loc")
	private String returnLoc;            // 반납 위치

	@Column(name = "return_lat")
	private int returnLat;            // 반납 위도

	@Column(name = "return_lon")
	private int returnLon;            // 반납 경도

	// 생성자: 외부에서 직접 호출하지 못하도록 private 으로 변경
	private RentEntity(CarEntity car, String rentUuid, LocalDateTime rentStime, LocalDateTime rentEtime,
		String renterName, String renterPhone, String purpose, String rentStatus, String rentLoc, int rentLat,
		int rentLon, String returnLoc, int returnLat, int returnLon) {
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
	public static RentEntity create(CarEntity car, String rentUuid, LocalDateTime rentStime, LocalDateTime rentEtime,
		String renterName, String renterPhone, String purpose, String rentStatus, String rentLoc, int rentLat,
		int rentLon, String returnLoc, int returnLat, int returnLon) {
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

	public void update(CarEntity car, LocalDateTime rentStime, LocalDateTime rentEtime,
		String renterName, String renterPhone, String purpose, String rentStatus,
		String rentLoc, String returnLoc) {
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
}
