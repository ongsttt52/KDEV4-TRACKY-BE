package kernel360.trackyweb.dashboard.domain;

import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import lombok.Data;

@Data
public class CarStatusTemp {

	private CarStatus status;
	private Long count;

	// 천승준 - jpa @Query 때문에 private -> public
	public CarStatusTemp(CarStatus status, Long count) {
		this.status = status;
		this.count = count;
	}

	public static CarStatusTemp create(CarStatus status, Long count) {
		return new CarStatusTemp(status, count);
	}

}
