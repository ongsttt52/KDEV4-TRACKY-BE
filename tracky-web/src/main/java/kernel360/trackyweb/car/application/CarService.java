package kernel360.trackyweb.car.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackycore.core.infrastructure.exception.BizException;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackycore.core.infrastructure.exception.DeviceException;
import kernel360.trackyweb.car.application.dto.request.CarCreateRequest;
import kernel360.trackyweb.car.application.dto.request.CarUpdateRequest;
import kernel360.trackyweb.car.application.dto.response.CarDetailResponse;
import kernel360.trackyweb.car.application.dto.response.CarResponse;
import kernel360.trackyweb.car.application.dto.response.CarSseEvent;
import kernel360.trackyweb.car.infrastructure.repo.CarBizRepository;
import kernel360.trackyweb.car.infrastructure.repo.CarRepository;
import kernel360.trackyweb.car.infrastructure.repo.DeviceRepository;
import kernel360.trackyweb.emitter.EventEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

	private final CarRepository carRepository;
	private final DeviceRepository deviceRepository;
	private final CarBizRepository carBizRepository;

	private final EventEmitterService eventEmitterService;

	/**
	 * 등록 차량 전체 조회
	 * @return 전체 차량 List
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<CarResponse>> getAll() {
		return ApiResponse.success(CarResponse.fromList(carRepository.findAll()));
	}

	/**
	 * mdn이 일치하는 차량 찾기
	 * @param mdn 차량 mdn
	 * @return 차량 단건 조회
	 */
	@Transactional(readOnly = true)
	public ApiResponse<Boolean> isMdnExist(String mdn) {
		return ApiResponse.success(carRepository.existsByMdn(mdn));
	}

	/**
	 * 필터링 기반 검색
	 * @param mdn
	 * @param status
	 * @param purpose
	 * @return 검색된 차량 List
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<CarResponse>> searchByFilter(String mdn, String status, String purpose,
		Pageable pageable) {
		Page<CarEntity> cars = carRepository.searchByFilter(mdn, status, purpose, pageable);
		Page<CarResponse> carResponses = cars.map(CarResponse::from);
		PageResponse pageResponse = PageResponse.from(carResponses);

		return ApiResponse.success(carResponses.getContent(), pageResponse);
	}

	/**
	 * 차량 MDN 값으로 단건 검색
	 * @param mdn
	 * @return 단건 차량 데이터
	 */
	@Transactional(readOnly = true)
	public ApiResponse<CarResponse> searchOneByMdn(String mdn) {
		CarEntity car = carRepository.findByMdn(mdn)
			.orElseThrow(CarException::notFound);
		return ApiResponse.success(CarResponse.from(car));
	}

	/**
	 * 디바이스 정보를 포함한 차량 MDN 값으로 검색
	 * @param mdn 차량 MDN
	 * @return 단건 차량 데이터 + 디바이스 정보
	 */
	@Transactional(readOnly = true)
	public ApiResponse<CarDetailResponse> searchOneDetailByMdn(String mdn) {
		CarEntity car = carRepository.findDetailByMdn(mdn)
			.orElseThrow(CarException::notFound);
		return ApiResponse.success(CarDetailResponse.from(car));
	}

	/**
	 * 차량 신규 등록 ( device는 기본 device 설정 MDN 1 가져옴 )
	 * @param carCreateRequest
	 * @return 등록 성공한 차량 detail
	 */
	@Transactional
	public ApiResponse<CarDetailResponse> create(CarCreateRequest carCreateRequest) {
		DeviceEntity device = deviceRepository.findById(1L)
			.orElseThrow(DeviceException::notFound);

		// device 세팅 넣은 car 객체 <- 임시로 모든 차량은 device 세팅 1번
		BizEntity biz = carBizRepository.findById(1L)
			.orElseThrow(BizException::notFound);

		CarEntity car = CarEntity.create(
			carCreateRequest.mdn(), biz, device, carCreateRequest.carType(), carCreateRequest.carPlate(),
			carCreateRequest.carYear(), carCreateRequest.purpose(), carCreateRequest.status(), carCreateRequest.sum()
		);

		if (carRepository.existsByMdn(carCreateRequest.mdn())) {
			throw CarException.duplicated();
		}

		CarEntity savedCar = carRepository.save(car);

		CarDetailResponse response = CarDetailResponse.from(savedCar);

		CarSseEvent carSseEvent = CarSseEvent.create("car_event", "create", "차량을 등록 하였습니다.");
		eventEmitterService.sendEvent("car_event", carSseEvent);

		return ApiResponse.success(response);
	}

	/**
	 * 차량 정보 수정
	 * @param mdn 차량 mdn
	 * @param carUpdateRequest 차량 정보
	 * @return 수정된 차량 detail
	 */
	@Transactional
	public ApiResponse<CarDetailResponse> update(String mdn, CarUpdateRequest carUpdateRequest) {
		CarEntity car = carRepository.findDetailByMdn(mdn)
			.orElseThrow(() -> CarException.notFound());

		BizEntity biz = carBizRepository.findById(1L)
			.orElseThrow(() -> BizException.notFound());

		// 항상 MDN 1인 디바이스 사용
		DeviceEntity device = deviceRepository.findById(1L)
			.orElseThrow(() -> DeviceException.notFound());

		// update 할 객체 생성
		car.updateFrom(biz, device, carUpdateRequest.carType(), carUpdateRequest.carPlate(),
			carUpdateRequest.carYear(), carUpdateRequest.purpose(), carUpdateRequest.status(), carUpdateRequest.sum());

		CarEntity updatedCar = carRepository.save(car);

		CarSseEvent carSseEvent = CarSseEvent.create("car_event", "update", "차량을 수정 하였습니다.");
		eventEmitterService.sendEvent("car_event", carSseEvent);

		return ApiResponse.success(CarDetailResponse.from(updatedCar));
	}

	/**
	 * 차량 삭제 API
	 * @param mdn
	 * @return ApiResponse
	 */
	@Transactional
	public ApiResponse<String> delete(String mdn) {
		carRepository.deleteByMdn(mdn);

		CarSseEvent carSseEvent = CarSseEvent.create("car_event", "delete", "차량을 삭제 하였습니다.");
		eventEmitterService.sendEvent("car_event", carSseEvent);

		return ApiResponse.success("삭제 완료");
	}
}
