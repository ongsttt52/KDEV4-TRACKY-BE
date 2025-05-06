package kernel360.trackyweb.admin.statistic.application.dto;

import kernel360.trackycore.core.domain.entity.AdminStatisticEntity;

public record AdminStatisticResponse() {

	public static AdminStatisticResponse from(AdminStatisticEntity adminStatistic) {
	}
}
