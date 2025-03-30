package kernel360.trackyemulator.application.mapper;

import kernel360.trackyemulator.application.service.util.RandomLocationGenerator;
import kernel360.trackyemulator.domain.EmulatorInstance;
import kernel360.trackyemulator.presentation.dto.CarOnOffRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CarOnOffRequestMapper {

    private final RandomLocationGenerator locationGenerator;

    //Start Request Dto
    public CarOnOffRequest toCarOnRequest(EmulatorInstance car) {
        return CarOnOffRequest.builder()
                .mdn(car.getMdn())
                .tid(car.getTid())
                .mid(car.getMid())
                .pv(car.getPv())
                .did(car.getDid())
                .onTime(LocalDateTime.now())
                .gcd("A")
                .lat(locationGenerator.randomLatitude())
                .lon(locationGenerator.randomLongitude())
                .ang(0)
                .spd(0)
                .sum(0)
                .build();
    }

    //Stop Request Dto
    public CarOnOffRequest toCarOffRequest(EmulatorInstance car) {
        return CarOnOffRequest.builder()
                .mdn(car.getMdn())
                .tid(car.getTid())
                .mid(car.getMid())
                .pv(car.getPv())
                .did(car.getDid())
                .onTime(LocalDateTime.now())
                .gcd("A")
                .lat(car.getCycleLastLat())
                .lon(car.getCycleLastLon())
                .ang(car.getCycleLastAng())
                .spd(car.getCycleLastSpeed())
                .sum(car.getSum())
                .build();
    }


}
