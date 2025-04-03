package kernel360.trackyweb.car.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.entity.CarEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;
import kernel360.trackycore.core.infrastructure.exception.CarException;
import kernel360.trackycore.core.infrastructure.exception.DeviceException;
import kernel360.trackyweb.car.infrastructure.repository.CarRepository;
import kernel360.trackyweb.car.infrastructure.repository.DeviceRepository;
import kernel360.trackyweb.car.presentation.dto.CarCreateRequest;
import kernel360.trackyweb.car.presentation.dto.CarDetailResponse;
import kernel360.trackyweb.car.presentation.dto.CarUpdateRequest;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarService carService;

	DeviceEntity device;
	CarEntity car;
	CarEntity updatedCar;
	CarCreateRequest carCreateRequest;
	CarUpdateRequest carUpdateRequest;

	@BeforeEach
	void setUp() {
		carCreateRequest = new CarCreateRequest("0123456789", 1L, device, "SUV", "대전 12가3456", "2020", "렌트카", "운행중",
			10000.0);
		carUpdateRequest = new CarUpdateRequest(1L, device, "세단", "대전 12가 3456", "2020", "렌트카", "운행중", 10000.0);

		device = DeviceEntity.create("A001", "6", "5", "1");
		car = CarEntity.create(
			"0123456789",
			1L,
			device,
			"SUV",
			"대전 12가 3456",
			"2020",
			"렌트카",
			"운행중",
			10000.0
		);
		updatedCar = CarEntity.create(
			"0123456789",
			1L,
			device,
			"세단",
			"대전 12가 3456",
			"2020",
			"렌트카",
			"운행중",
			10000.0
		);
	}

	@Test
	@DisplayName("차량 등록 요청이 성공적으로 DB에 저장되었으며 정상적인 응답을 반환하는지 확인")
	void createSuccess() {
		// when
		when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
		when(carRepository.save(any(CarEntity.class))).thenReturn(car);

		ApiResponse<CarDetailResponse> saved = carService.create(carCreateRequest);

		// then
		assertNotNull(saved);
		assertEquals(200, saved.getStatus());
		assertEquals("success", saved.getMessage());
		assertEquals(carCreateRequest.mdn(), saved.getData().mdn());

		verify(deviceRepository, times(1)).findById((1L));
		verify(carRepository, times(1)).save(any(CarEntity.class));
	}

	@Test
	@DisplayName("차량 등록 시 잘못된 디바이스 번호로 요청할 경우 예외가 발생하는지 확인")
	void createFailedByDeviceNotFound() {
		// when
		when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

		// then
		assertThrows(DeviceException.class, () -> carService.create(carCreateRequest));

		verify(deviceRepository, times(1)).findById((1L));
		verify(carRepository, never()).save(any(CarEntity.class));
	}

	@Test
	@DisplayName("차량 등록 시 중복된 차량 번호로 요청할 경우 예외가 발생하는지 확인")
	void createFailedByMdnDuplicated() {
		// when
		when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
		when(carRepository.existsByMdn(carCreateRequest.mdn())).thenReturn(true);

		// then
		assertThrows(CarException.class, () -> carService.create(carCreateRequest));

		verify(deviceRepository, times(1)).findById((1L));
		verify(carRepository, times(1)).existsByMdn(carCreateRequest.mdn());
		verify(carRepository, never()).save(any(CarEntity.class));
	}

	@Test
	@DisplayName("차량 정보 수정 시 정보가 정상적으로 업데이트되는지 확인")
	void updateSuccess() {
		// when
		when(carRepository.findDetailByMdn(any())).thenReturn(Optional.of(car));
		when(deviceRepository.findById(any())).thenReturn(Optional.of(device));
		when(carRepository.save(any(CarEntity.class))).thenReturn(updatedCar);

		ApiResponse<CarDetailResponse> updated = carService.update(car.getMdn(), carUpdateRequest);

		// then
		assertNotNull(updated);
		assertEquals(200, updated.getStatus());
		assertEquals("success", updated.getMessage());
		assertEquals(car.getMdn(), updated.getData().mdn());
		assertEquals(carUpdateRequest.carType(), updated.getData().carType());

		verify(carRepository, times(1)).findDetailByMdn(car.getMdn());
		verify(deviceRepository, times(1)).findById(any());
		verify(carRepository, times(1)).save(any(CarEntity.class));
	}

	@Test
	@DisplayName("차량 정보 업데이트 시 차량을 찾지 못할 경우 예외가 발생하는지 확인")
	void updateFailedByCarNotFound() {
		// when
		when(carRepository.findDetailByMdn(any())).thenReturn(Optional.empty());

		// then
		assertThrows(CarException.class, () -> carService.update(car.getMdn(), carUpdateRequest));

		verify(carRepository, times(1)).findDetailByMdn(car.getMdn());
		verify(deviceRepository, never()).findById(any());
		verify(carRepository, never()).save(any(CarEntity.class));
	}

	@Test
	@DisplayName("차량 정보 업데이트 시 잘못된 디바이스 번호로 요청할 경우 예외가 발생하는지 확인")
	void updateFailedByDeviceNotFound() {
		// when
		when(carRepository.findDetailByMdn(any())).thenReturn(Optional.of(car));
		when(deviceRepository.findById(any())).thenReturn(Optional.empty());

		// then
		assertThrows(DeviceException.class, () -> carService.update(car.getMdn(), carUpdateRequest));

		verify(carRepository, times(1)).findDetailByMdn(car.getMdn());
		verify(deviceRepository, times(1)).findById(any());
		verify(carRepository, never()).save(any(CarEntity.class));
	}
}