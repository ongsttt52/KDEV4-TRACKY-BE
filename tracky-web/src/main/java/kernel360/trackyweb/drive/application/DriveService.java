package kernel360.trackyweb.drive.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import kernel360.trackyweb.drive.application.dto.response.CarListResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriveService {

	private final CarDomainProvider carDomainProvider;

	public ApiResponse<List<CarListResponse>> getCar() {
		List<CarListResponse> carList = carDomainProvider.getCar();

		return ApiResponse.success(carList);
	}
}
