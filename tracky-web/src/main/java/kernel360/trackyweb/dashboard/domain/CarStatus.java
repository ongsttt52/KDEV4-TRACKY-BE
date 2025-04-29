package kernel360.trackyweb.dashboard.domain;

import lombok.Data;

@Data
public class CarStatus {

	private String status;
	private Long count;

	// 천승준 - jpa @Query 때문에 private -> public
	public CarStatus(String status, Long count) {
		this.status = status;
		this.count = count;
	}

	public static CarStatus create(String status, Long count) {
		return new CarStatus(status, count);
	}

}
