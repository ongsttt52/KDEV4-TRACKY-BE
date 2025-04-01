package kernel360.trackyweb.car.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackycore.core.infrastructure.exception.DeviceException;
import kernel360.trackyweb.car.application.mapper.CarMapper;
import kernel360.trackyweb.car.presentation.dto.CarDetailResponse;
import kernel360.trackyweb.car.presentation.dto.CarRequest;
import kernel360.trackyweb.car.presentation.dto.CarResponse;
import kernel360.trackyweb.car.infrastructure.repository.CarRepository;
import kernel360.trackyweb.car.infrastructure.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

	private final CarRepository carRepository;
	private final DeviceRepository deviceRepository;

	/**
	 * 등록 차량 전체 조회
	 * @return 전체 차량 List
	 */
	public ApiResponse<List<CarResponse>> getAll() {
		return ApiResponse.success(CarResponse.fromList(carRepository.findAll()));
	}

	/**
	 * Mdn 기반 차량 검색
	 * @param mdn
	 * @return 검색된 차량 List
	 */
	public ApiResponse<List<CarResponse>> searchByMdn(String mdn) {
		return ApiResponse.success(CarResponse.fromList(carRepository.findByMdnContainingOrdered(mdn)));
	}

	/**
	 * 차량 ID 값으로 단건 검색
	 * @param id
	 * @return 단건 차량 데이터
	 */
	public ApiResponse<CarResponse> searchById(Long id) {
		CarEntity car = carRepository.findById(id)
			// 천승준 - 공통 에러 처리 해봤어요
			.orElseThrow(() -> CarException.notFound());
		return ApiResponse.success(CarResponse.from(car));
	}

	/**
	 * 디바이스 정보를 포함한 차량 ID 값으로 검색
	 * @param id 차량 ID
	 * @return 단건 차량 데이터 + 디바이스 정보
	 */
	public ApiResponse<CarDetailResponse> searchDetailById(Long id) {
		CarEntity car = carRepository.findDetailById(id)
			// 천승준 - 공통 에러 처리 해봤어요
			.orElseThrow(() -> CarException.notFound());
		return ApiResponse.success(CarDetailResponse.from(car));
	}

	/**
	 * 차량 신규 등록 ( device는 기본 device 설정 ID 1 가져옴 )
	 * @param carRequest
	 * @return 등록 성공한 차량 detail
	 */
	public ApiResponse<CarDetailResponse> create(CarRequest carRequest) {
		DeviceEntity device = deviceRepository.findById(1L)
			.orElseThrow(() -> DeviceException.notFound());

		// device 세팅 넣은 car 객체 <- 임시로 모든 차량은 device 세팅 1번
		CarEntity car = CarMapper.createCar(carRequest, device);

		CarEntity savedCar = carRepository.save(car);

		CarDetailResponse response = CarDetailResponse.from(savedCar);

		return ApiResponse.success(response);
	}

	/**
	 * 차량 정보 수정
	 * @param id 차량 ID
	 * @param carRequest 차량 정보
	 * @return 수정된 차량 detail
	 */
	public ApiResponse<CarDetailResponse> update(Long id, CarRequest carRequest) {
		CarEntity car = carRepository.findDetailById(id)
			.orElseThrow(() -> CarException.notFound());

		// 항상 ID 1인 디바이스 사용
		DeviceEntity device = deviceRepository.findById(1L)
			.orElseThrow(() -> DeviceException.notFound());

		// update 할 객체 생성
		CarMapper.updateCar(car, carRequest, device);

		log.info("업데이트 차량 : {}", car);

		CarEntity updatedCar = carRepository.save(car);
		return ApiResponse.success(CarDetailResponse.from(updatedCar));
	}

	/**
	 * 차량 삭제 API
	 * @param id
	 * @return ApiResponse
	 */
	public ApiResponse<String> delete(Long id) {
		carRepository.deleteById(id);
		return ApiResponse.success("삭제 완료");
	}

}
