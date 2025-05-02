package kernel360.trackyweb.car.application.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import kernel360.trackycore.core.domain.entity.CarEntity;
import kernel360.trackycore.core.domain.entity.enums.CarStatus;
import kernel360.trackycore.core.domain.entity.enums.CarType;

public record CarsExportRequest(
	CarStatus status,
	String carPlate,
	CarType carType,
	String mdn,
	String carYear
) {

	public static CarsExportRequest create(CarEntity car) {
		return new CarsExportRequest(
			car.getStatus(),
			car.getCarPlate(),
			car.getCarType(),
			car.getMdn(),
			car.getCarYear());
	}

	public static List<CarsExportRequest> from(List<CarEntity> cars) {
		return cars.stream()
			.map(CarsExportRequest::create)
			.collect(Collectors.toList());
	}
}
