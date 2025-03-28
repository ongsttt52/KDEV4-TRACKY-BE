package kernel360.trackyweb.car.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.trackyweb.car.application.CarService;
import kernel360.trackyweb.car.domain.CarResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
@Tag(name = "Car API", description = "차량에 관련한 기본 CRUD API 입니다")
public class CarController {

	private final CarService carService;

	@GetMapping("all")
	@Operation(summary = "전체 차량 조회", description = "DB에 있는 모든 차량 리스트 조회")
	public List<CarResponse> getAll() {
		return carService.getAll();
	}

	@GetMapping("get/{id}")
	@Operation(summary = "전체 차량 조회", description = "DB에 있는 모든 차량 리스트 조회")

}
