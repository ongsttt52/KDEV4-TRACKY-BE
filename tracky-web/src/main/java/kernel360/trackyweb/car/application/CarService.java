package kernel360.trackyweb.car.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackycore.core.common.provider.BizProvider;
import kernel360.trackycore.core.common.provider.CarProvider;
import kernel360.trackycore.core.common.provider.DeviceProvider;
import kernel360.trackycore.core.common.sse.GlobalSseEvent;
import kernel360.trackycore.core.common.sse.SseEvent;
import kernel360.trackyweb.car.application.dto.request.CarCreateRequest;
import kernel360.trackyweb.car.application.dto.request.CarSearchByFilterRequest;
import kernel360.trackyweb.car.application.dto.request.CarUpdateRequest;
import kernel360.trackyweb.car.application.dto.response.CarDetailResponse;
import kernel360.trackyweb.car.application.dto.response.CarResponse;
import kernel360.trackyweb.car.domain.provider.CarDomainProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

	private final DeviceProvider deviceProvider;
	private final BizProvider bizProvider;
	private final CarProvider carProvider;
	private final GlobalSseEvent globalSseEvent;

	private final CarDomainProvider carDomainProvider;

	/**
	 * mdn이 일치하는 차량 찾기
	 * @param mdn 차량 mdn
	 * @return 차량 단건 조회
	 */
	@Transactional(readOnly = true)
	public ApiResponse<Boolean> existsByMdn(String mdn) {
		return ApiResponse.success(carProvider.existsByMdn(mdn));
	}

	/**
	 * 필터링 기반 검색\
	 * @return 검색된 차량 List
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<CarResponse>> getAllBySearchFilter(CarSearchByFilterRequest carSearchByFilterRequest) {
		Page<CarEntity> cars = carDomainProvider.searchByFilter(carSearchByFilterRequest);
		Page<CarResponse> carResponses = cars.map(CarResponse::from);
		PageResponse pageResponse = PageResponse.from(carResponses);

		return ApiResponse.success(carResponses.getContent(), pageResponse);
	}

	/**
	 * 디바이스 정보를 포함한 차량 MDN 값으로 검색
	 * @param mdn 차량 MDN
	 * @return 단건 차량 데이터 + 디바이스 정보
	 */
	@Transactional(readOnly = true)
	public ApiResponse<CarDetailResponse> searchOne(String mdn) {
		CarEntity car = carDomainProvider.getCarDetail(mdn);
		return ApiResponse.success(CarDetailResponse.from(car));
	}

	/**
	 * 차량 신규 등록 ( device는 기본 device 설정 MDN 1 가져옴 )
	 * @param carCreateRequest
	 * @return 등록 성공한 차량 detail
	 */
	@Transactional
	public ApiResponse<CarDetailResponse> create(CarCreateRequest carCreateRequest) {
		DeviceEntity device = deviceProvider.getDevice(1L);

		// device 세팅 넣은 car 객체 <- 임시로 모든 차량은 device 세팅 1번
		BizEntity biz = bizProvider.getBiz(1L);

		carDomainProvider.existsByMdn(carCreateRequest.mdn());

		CarEntity car = CarEntity.create(
			carCreateRequest.mdn(), biz, device, carCreateRequest.carType(), carCreateRequest.carPlate(),
			carCreateRequest.carYear(), carCreateRequest.purpose(), carCreateRequest.status(), carCreateRequest.sum()
		);

		CarEntity savedCar = carDomainProvider.save(car);

		CarDetailResponse response = CarDetailResponse.from(savedCar);

		globalSseEvent.sendEvent(SseEvent.CAR_CREATED);

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
		CarEntity car = carDomainProvider.getCar(mdn);

		BizEntity biz = bizProvider.getBiz(1L);

		// 항상 MDN 1인 디바이스 사용
		DeviceEntity device = deviceProvider.getDevice(1L);

		// update 할 객체 생성
		car.updateFrom(biz, device, carUpdateRequest.carType(), carUpdateRequest.carPlate(),
			carUpdateRequest.carYear(), carUpdateRequest.purpose(), carUpdateRequest.status(), carUpdateRequest.sum());

		globalSseEvent.sendEvent(SseEvent.CAR_UPDATED);

		return ApiResponse.success(CarDetailResponse.from(car));
	}

	/**
	 * 차량 삭제 API
	 * @param mdn
	 * @return ApiResponse
	 */
	@Transactional
	public ApiResponse<String> delete(String mdn) {
		carDomainProvider.delete(mdn);
		globalSseEvent.sendEvent(SseEvent.CAR_DELETED);

		return ApiResponse.success("삭제 완료");
	}
}
