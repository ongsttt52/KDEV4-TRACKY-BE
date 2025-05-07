package kernel360.trackyweb.car.domain;


import kernel360.trackycore.core.domain.entity.enums.CarStatus;

public record MdnStatus(
        String mdn,
        String carPlate,
        CarStatus status
) { }

