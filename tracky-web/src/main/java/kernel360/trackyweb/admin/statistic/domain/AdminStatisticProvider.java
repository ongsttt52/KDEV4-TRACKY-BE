package kernel360.trackyweb.admin.statistic.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizListResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizMonthlyResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.AdminBizStatisticResponse;
import kernel360.trackyweb.admin.statistic.application.dto.response.HourlyGraphResponse;
import kernel360.trackyweb.admin.statistic.infrastructure.AdminStatisticRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminStatisticProvider {

	private final AdminStatisticRepositoryCustom adminStatisticRepository;

	public List<AdminBizListResponse> getAdminBizList() {
		return adminStatisticRepository.fetchAdminBizList();
	}

	public AdminBizStatisticResponse getDriveStatByBizIdAndDate(Long bizId, LocalDate selectedDate) {

		AdminBizStatisticResponse response = adminStatisticRepository.getDriveStatByBizIdAndDate(bizId,
			selectedDate);

		if (response == null) {
			return new AdminBizStatisticResponse(0, 0, 0);
		}
		return response;
	}

	public List<AdminBizMonthlyResponse> getAdminBizMonthlyDriveCount(Long bizId, LocalDate selectedDate) {

		List<AdminBizMonthlyResponse> response = adminStatisticRepository.getTotalDriveCountInOneYear(bizId,
			selectedDate);

		if (response == null) {
			return List.of();
		}
		return response;
	}

	public List<HourlyGraphResponse> getAdminBizHourlyDriveCount(Long bizId, LocalDate selectedDate) {

		List<HourlyGraphResponse> response = adminStatisticRepository.getHourlyDriveCounts(bizId, selectedDate);

		if (response == null) {
			return List.of();
		}
		return response;
	}
}
