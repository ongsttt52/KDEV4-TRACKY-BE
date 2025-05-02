package kernel360.trackyweb.car.application.dto.response;

import java.util.List;

import kernel360.trackycore.core.domain.entity.CarEntity;

public record MdnWithBizResponse(
	String mdn,
	Long bizId
) {
	public static MdnWithBizResponse of(String mdn, Long bizId) {
		return new MdnWithBizResponse(mdn, bizId);
	}

	public static List<MdnWithBizResponse> ofList(List<CarEntity> cars) {
		return cars.stream()
			.map(car -> MdnWithBizResponse.of(car.getMdn(), car.getBiz().getId()))
			.toList();
	}
}
