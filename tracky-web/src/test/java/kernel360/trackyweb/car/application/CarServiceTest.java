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
import kernel360.trackycore.core.infrastructure.exception.DeviceException;
import kernel360.trackyweb.car.infrastructure.repository.CarRepository;
import kernel360.trackyweb.car.infrastructure.repository.DeviceRepository;
import kernel360.trackyweb.car.presentation.dto.CarCreateRequest;
import kernel360.trackyweb.car.presentation.dto.CarDetailResponse;

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
	CarCreateRequest carCreateRequest;

	@BeforeEach
	void setUp() {
		device = DeviceEntity.create("A001", "6", "5", "1");
		car = CarEntity.create(
			"0123456789",
			123L,
			device,
			"SUV",
			"12가3456",
			"2020",
			"렌트카",
			"운행중",
			100.0
		);
	}

	@Test
	@DisplayName("차량 등록 요청이 성공적으로 DB에 저장되었으며 정상적인 응답을 반환하는지 확인")
	void createSuccess() {
		// given
		carCreateRequest = new CarCreateRequest("0123456789", 123L, device, "Sedan", "12가3456", "2020", "렌트카", "운행중",
			100.0);

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
	@DisplayName("잘못된 디바이스 번호로 요청할 경우 예외가 발생하는지 확인")
	void createFailedByDeviceNotFound() {
		// given
		carCreateRequest = new CarCreateRequest("0123456789", 1L, device, "Sedan", "12가3456", "2020", "렌트카", "운행중",
			100.0);

		// when
		when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

		// then
		assertThrows(DeviceException.class, () -> carService.create(carCreateRequest));

		verify(deviceRepository, times(1)).findById((1L));
		verify(carRepository, never()).save(any(CarEntity.class));
	}

	@Test
	void update() {
	}
}