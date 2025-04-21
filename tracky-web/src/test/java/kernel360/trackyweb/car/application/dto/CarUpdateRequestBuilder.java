package kernel360.trackyweb.car.application.dto;

import kernel360.trackycore.core.common.entity.BizEntity;
import kernel360.trackycore.core.common.entity.DeviceEntity;

public class CarUpdateRequestBuilder {
	BizEntity biz;
	DeviceEntity device;
	String carType;
	String carPlate;
	String carYear;
	String purpose;
	String status;
	double sum;
}
