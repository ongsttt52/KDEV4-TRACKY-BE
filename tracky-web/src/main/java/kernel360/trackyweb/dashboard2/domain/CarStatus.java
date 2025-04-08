package kernel360.trackyweb.dashboard2.domain;

import lombok.Data;

@Data
public class CarStatus {

	private String status;
	private long count;

	// 천승준 - jpa @Query 때문에 private -> public
	public CarStatus(String status, long count) {
		this.status = status;
		this.count = count;
	}

	public static CarStatus create(String status, long count) {
		return new CarStatus(status, count);
	}

}
