package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "car")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarEntity extends DateBaseEntity {

	@Id
	private String mdn;           // 차량식별키

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "biz_id")
	private BizEntity biz;         // 업체 ID 외래키

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	private DeviceEntity device;    // 디바이스 세팅ID 외래키

	@Column(name = "car_type")
	private String carType;       // 차종

	@Column(name = "car_plate")
	private String carPlate;      // 번호판

	@Column(name = "car_year")
	private String carYear;       // 연식

	private String purpose;       // 차량용도
	private String status;        // 차량상태
	private double sum;           // 누적 주행 거리

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;   // 삭제 시간

	private CarEntity(String mdn, BizEntity biz, DeviceEntity device, String carType, String carPlate, String carYear,
		String purpose, String status, double sum) {

		this.mdn = mdn;
		this.biz = biz;
		this.device = device;
		this.carType = carType;
		this.carPlate = carPlate;
		this.carYear = carYear;
		this.purpose = purpose;
		this.status = status;
		this.sum = sum;
	}

	public static CarEntity create(String mdn, BizEntity biz, DeviceEntity device, String carType, String carPlate,
		String carYear, String purpose, String status, double sum) {
		return new CarEntity(mdn, biz, device, carType, carPlate, carYear, purpose, status, sum);
	}

	public void updateFrom(
		BizEntity biz,
		DeviceEntity device,
		String carType,
		String carPlate,
		String carYear,
		String purpose,
		String status,
		double sum
	) {
		this.biz = biz;
		this.device = device;
		this.carType = carType;
		this.carPlate = carPlate;
		this.carYear = carYear;
		this.purpose = purpose;
		this.status = status;
		this.sum = sum;
	}

	public void updateDistance(double additionalDistance) {
		this.sum += additionalDistance;
	}
}
