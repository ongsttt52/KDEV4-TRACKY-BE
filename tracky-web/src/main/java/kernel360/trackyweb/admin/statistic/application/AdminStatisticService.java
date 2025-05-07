package kernel360.trackyweb.admin.statistic.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.AdminStatisticEntity;
import kernel360.trackyweb.admin.statistic.application.dto.AdminStatisticResponse;
import kernel360.trackyweb.admin.statistic.domain.AdminStatisticProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStatisticService {

	private final AdminStatisticProvider adminStatisticProvider;

	public ApiResponse<AdminStatisticResponse> getAdminStatistic(LocalDate date) {
		AdminStatisticEntity adminStatistic = adminStatisticProvider.getAdminStatistic(date);

		AdminStatisticResponse response = AdminStatisticResponse.from(adminStatistic);

		return ApiResponse.success(response);
	}
}
