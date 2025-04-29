package kernel360.trackyweb.car.application.dto.response;

import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackycore.core.domain.entity.DeviceEntity;

public record BizResponse (
	Long id,
	String bizName,
	String bizRegNum,
	String bizAdmin,
	String bizPhoneNum
) {
	public static BizResponse from(BizEntity biz) {
		return new BizResponse(
			biz.getId(),
			biz.getBizName(),
			biz.getBizRegNum(),
			biz.getBizAdmin(),
			biz.getBizPhoneNum()
		);
	}
}
