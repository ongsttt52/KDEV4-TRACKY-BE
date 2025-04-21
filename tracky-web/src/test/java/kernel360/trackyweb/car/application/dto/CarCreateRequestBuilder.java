package kernel360.trackyweb.car.application.dto;

import kernel360.trackyweb.car.application.dto.request.CarCreateRequest;

public class CarCreateRequestBuilder {
	private String mdn = "test_mdn_0";
	private Long bizId = 1L;
	private Long device = 1L;
	private String carType = "test_carType";
	private String carPlate = "test_carPlate";
	private String carYear = "test_carYear";
	private String purpose = "test_purpose";
	private String status = "test_status";
	private double sum = 10000.0;

	public CarCreateRequestBuilder carType(String carType) {
		this.carType = carType;
		return this;
	}

	public CarCreateRequestBuilder carPlate(String carPlate) {
		this.carPlate = carPlate;
		return this;
	}

	public CarCreateRequestBuilder carYear(String carYear) {
		this.carYear = carYear;
		return this;
	}

	public CarCreateRequestBuilder purpose(String purpose) {
		this.purpose = purpose;
		return this;
	}

	public CarCreateRequestBuilder status(String status) {
		this.status = status;
		return this;
	}

	public CarCreateRequestBuilder sum(double sum) {
		this.sum = sum;
		return this;
	}

	public CarCreateRequest build() {
		return new CarCreateRequest(
			mdn,
			bizId,
			device,
			carType,
			carPlate,
			carYear,
			purpose,
			status,
			sum
		);
	}
}
